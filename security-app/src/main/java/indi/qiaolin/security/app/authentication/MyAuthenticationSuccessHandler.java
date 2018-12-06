package indi.qiaolin.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.qiaolin.security.core.property.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 认证成功处理器
 * 需要实现  AuthenticationSuccessHandler接口，
 * 我们这里使用他的子类， SavedRequestAwareAuthenticationSuccessHandler
 * 这个子类的  onAuthenticationSuccess 方法实现了跳转到登陆之前访问的页面
 * @author qiaolin
 * @version 2018/11/26
 **/

@Slf4j
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    /** 用于检查第三方应用的clientId和clientSecret是否正确 */
    @Autowired
    private ClientDetailsService clientDetailsService;

    /** 生成授权码的服务 */
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;


    /**
     *  认证成功时调用
     * @param request
     * @param response
     * @param authentication 认证对象，里面包含了用户的认证信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("登陆成功！");
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中无Client信息！");
        }


        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        /** 这个为第三方程序的信息 */
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if(clientDetails == null){
            throw new UnapprovedClientAuthenticationException("ClientId对应的信息不存在！");
        }else if(!clientDetails.getClientSecret().equals(clientSecret)){
            throw new UnapprovedClientAuthenticationException("ClientSecret不匹配！");
        }

        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

        OAuth2Request storedRequest = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(storedRequest, authentication);

        // 这个就是生成出来的令牌
        OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(objectMapper.writeValueAsString(accessToken));


    }

    /**
     * 提取Basic认证信息并解码
     * @throws BadCredentialsException 如果认证的凭证错误或者是没有的
     * Base64
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request)throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        }catch (IllegalArgumentException e) {
            throw new BadCredentialsException("解码Basic认证凭证失败");
        }

        String token = new String(decoded, "UTF-8");

        int delimit = token.indexOf(":");

        if (delimit == -1) {
            throw new BadCredentialsException("无效的Basic认证凭证！");
        }
        return new String[] { token.substring(0, delimit), token.substring(delimit + 1) };
    }
}
