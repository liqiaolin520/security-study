package indi.qiaolin.security.core.validate.code;

import indi.qiaolin.security.core.property.SecurityConstants;
import indi.qiaolin.security.core.property.SecurityProperties;
import indi.qiaolin.security.core.validate.code.image.ImageCode;
import indi.qiaolin.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码处理过滤器,支持图形验证码与短信验证码
 * OncePerRequestFilter 这个filter接口保证过滤器只调用一次
 * @author qiaolin
 * @version 2018/11/26
 **/

@Slf4j
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
    private static final String IMAGE_CODE_SESSION_KEY = AbstractValidateCodeProcessor.SESSION_KEY_PREFIX + "IMAGE";

    /** session的操作类 */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /** 认证失败处理器 */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /** 需要拦截并验证验证码的路径,以及验证的类型  */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    /** 安全配置类 */
    @Autowired
    private SecurityProperties securityProperties;

    /** 路径匹配工具类 */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /** 验证流程处理器操作类 */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType validateCodeType = getValidateTypeByRequest(request);
        if(validateCodeType != null){
            log.info("校验请求({})中的验证码，验证码类型{}", request.getRequestURI(), validateCodeType);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(validateCodeType).validate(new ServletWebRequest(request, response));
                log.info("请求({})中的验证码校验通过");
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
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);

        if(StringUtils.isBlank(codeInRequest)){
            throw new ValidateCodeException("验证码不能为空！");
        }

        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, IMAGE_CODE_SESSION_KEY);
        if(codeInSession == null){
            throw new ValidateCodeException("验证码不存在！");
        }

        if(codeInSession.isExpire()){
            sessionStrategy.removeAttribute(request, IMAGE_CODE_SESSION_KEY);
            throw new ValidateCodeException("验证码已过期！");
        }

        if(!StringUtils.equals(codeInRequest, codeInSession.getCode())){
            throw new ValidateCodeException("验证码不匹配！");
        }

    }

    /**
     *  初始化拦截的url
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        // 图形验证码需要拦截的地址
        urlMap.put(SecurityConstants.DEFAULT_AUTHENTICATION_URL_FORM, ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

        // 短信验证码需要拦截的地址
        urlMap.put(SecurityConstants.DEFAULT_AUTHENTICATION_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
    }


    /**
     *  将要拦截的url以逗号分割，并加入到Map中
     * @param urlString 待分割的url
     * @param validateCodeType 处理这个地址的验证类型
     */
    private void addUrlToMap(String urlString, ValidateCodeType validateCodeType){
        if(StringUtils.isBlank(urlString)){
            String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String configUrl : configUrls) {
                urlMap.put(configUrl, validateCodeType);
            }
        }
    }

    /**
     *  根据请求URI来找有没有对应的验证码处理类型
     * @param request
     * @return
     */
    private ValidateCodeType getValidateTypeByRequest(HttpServletRequest request){
        if(!StringUtils.equalsIgnoreCase(request.getMethod(), HttpMethod.GET.name())){
            String requestURI = request.getRequestURI();
            for (String url : urlMap.keySet()) {
                if(pathMatcher.match(url, requestURI)){
                    return urlMap.get(url);
                }
            }
        }
        return null;
    }

}
