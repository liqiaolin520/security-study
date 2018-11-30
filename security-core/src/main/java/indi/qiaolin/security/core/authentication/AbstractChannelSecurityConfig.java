package indi.qiaolin.security.core.authentication;

import indi.qiaolin.security.core.property.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author qiaolin
 * @version 2018/11/30
 **/
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler authenticationFailureHandler;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        // 登陆方式为form表单，spring security会有一个默认表单
        http.formLogin()

            // 用户未认证时跳转的地址
            .loginPage(SecurityConstants.DEFAULT_UN_AUTHENTICATION_URL)

            // 配置了自定义登陆页面，这个一定要自己重新配置一下，否则没有login这个登陆接口
            // 这个是处理登陆请求流程的地址，即认证的地址
            .loginProcessingUrl(SecurityConstants.DEFAULT_AUTHENTICATION_URL_FORM)

            // 认证成功处理器
            .successHandler(authenticationSuccessHandler)

            // 认证失败处理器
            .failureHandler(authenticationFailureHandler);
    }


}
