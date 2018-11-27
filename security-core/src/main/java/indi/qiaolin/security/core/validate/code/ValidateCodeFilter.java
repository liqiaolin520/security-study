package indi.qiaolin.security.core.validate.code;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 图片验证码处理过滤器
 * OncePerRequestFilter 这个filter接口保证过滤器只调用一次
 * @author qiaolin
 * @version 2018/11/26
 **/

public class ValidateCodeFilter extends OncePerRequestFilter {

    /** session的操作类 */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /** 认证失败处理器 */
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * @param authenticationFailureHandler 认证失败处理器
     */
    public ValidateCodeFilter(AuthenticationFailureHandler authenticationFailureHandler){
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURL = request.getRequestURI();
        if("/login".equals(requestURL) && "POST".equalsIgnoreCase(request.getMethod())){
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     *  验证验证码是否正确
     * @param request
     */
    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

        if(StringUtils.isBlank(codeInRequest)){
            throw new ValidateCodeException("验证码不能为空！");
        }

        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, ValidateController.SESSION_KEY);
        if(codeInSession == null){
            throw new ValidateCodeException("验证码不存在！");
        }

        if(codeInSession.isExpire()){
            sessionStrategy.removeAttribute(request, ValidateController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期！");
        }

        if(!StringUtils.equals(codeInRequest, codeInSession.getCode())){
            throw new ValidateCodeException("验证码不匹配！");
        }

    }

}
