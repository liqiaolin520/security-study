package indi.qiaolin.security.app.social.openid;

import indi.qiaolin.security.core.property.SecurityConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理OpenId的拦截器
 * @author qiaolin
 * @version 2018/12/7
 **/

@Getter
@Setter
public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter{

    private String openIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_OPEN_ID;
    private String providerIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_PROVIDER_ID;
    private boolean postOnly = true;

    protected OpenIdAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_AUTHENTICATION_URL_OPENID, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(postOnly && !HttpMethod.POST.name().equals(request.getMethod())){
            throw new AuthenticationServiceException("Authentication method not supported：" + request.getMethod());
        }

        String openId = obtainOpenId(request);
        String providerId = obtainProviderId(request);

        openId = StringUtils.trimToEmpty(openId);
        providerId = StringUtils.trimToEmpty(providerId);

        OpenIdAuthenticationToken token = new OpenIdAuthenticationToken(openId, providerId);

        setDetails(request, token);

        return getAuthenticationManager().authenticate(token);
    }

    /**
     * 从请求中获取OpenId
     * @param request
     * @return
     */
    protected String obtainOpenId(HttpServletRequest request){
        return request.getParameter(openIdParameter);
    }

    /**
     *  从请求中获取提供者Id
     * @param request
     * @return
     */
    protected String obtainProviderId(HttpServletRequest request){
        return request.getParameter(providerIdParameter);
    }


    protected void setDetails(HttpServletRequest request, AbstractAuthenticationToken authentication){
        authentication.setDetails(authenticationDetailsSource.buildDetails(request));
    }

}
