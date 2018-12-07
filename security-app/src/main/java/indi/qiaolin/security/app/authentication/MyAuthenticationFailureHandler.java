package indi.qiaolin.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.qiaolin.security.app.social.AppSignUpUtils;
import indi.qiaolin.security.core.property.SecurityProperties;
import indi.qiaolin.security.core.social.support.SocialUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialAuthenticationRedirectException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 *  认证失败处理器
 *  需要实现 AuthenticationFailureHandler 接口
 *  但是这里我们用他的子类
 *  SimpleUrlAuthenticationFailureHandler
 *
 * @author qiaolin
 * @version 2018/11/26
 **/

@Slf4j
@Component
public class MyAuthenticationFailureHandler  extends SimpleUrlAuthenticationFailureHandler{

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private AppSignUpUtils appSignUpUtils;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Object result;
        if(SocialAuthenticationRedirectException.class.isAssignableFrom(exception.getClass())){
            Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
            SocialUserInfo socialUserInfo = new SocialUserInfo();
            ConnectionKey key = connection.getKey();
            socialUserInfo.setProviderId(key.getProviderId());
            socialUserInfo.setProviderUserId(key.getProviderUserId());
            socialUserInfo.setNickName(connection.getDisplayName());
            socialUserInfo.getHeadImg();
            appSignUpUtils.saveConnectionData(new ServletWebRequest(request), connection.createData());
            result = socialUserInfo;
        }else{
            result = exception.getMessage();
        }
        // 认证失败 401
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // 设置响应格式与编码
        response.setContentType("application/json;charset=UTF-8");

        // 写入响应信息
        response.getWriter().print(objectMapper.writeValueAsString(result));
        exception.printStackTrace();

    }

}
