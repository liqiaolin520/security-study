package indi.qiaolin.security.app;

import indi.qiaolin.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import indi.qiaolin.security.core.property.SecurityConstants;
import indi.qiaolin.security.core.property.SecurityProperties;
import indi.qiaolin.security.core.social.MySpringSocialConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源服务器配置
 * @author qiaolin
 * @version 2018/12/6
 **/

@Configuration
@EnableResourceServer // 必须加上这个注解，否则无法使用令牌访问接口
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private MySpringSocialConfigurer mySpringSocialConfigurer;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
            .loginPage(SecurityConstants.DEFAULT_UN_AUTHENTICATION_URL)
            .loginProcessingUrl(SecurityConstants.DEFAULT_AUTHENTICATION_URL_FORM)
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler)

            .and()
            .apply(smsCodeAuthenticationSecurityConfig)
            .and()
            .apply(mySpringSocialConfigurer)
            .and()
            .authorizeRequests()
            .antMatchers(buildPermitUrl())
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .csrf()
            .disable();

    }

    private String[] buildPermitUrl(){
        List<String> permitUrl = new ArrayList<>();

        permitUrl.add(securityProperties.getBrowser().getLoginPage());
        permitUrl.add(SecurityConstants.DEFAULT_UN_AUTHENTICATION_URL);
        permitUrl.add(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*");
        permitUrl.add(SecurityConstants.DEFAULT_AUTHENTICATION_URL_MOBILE);
        permitUrl.add(securityProperties.getBrowser().getSignUpUrl());
        permitUrl.add("/user/register");
        permitUrl.add("/social/user");
        permitUrl.add(securityProperties.getBrowser().getSession().getSessionInvalidUrl() + ".html");
        permitUrl.add(securityProperties.getBrowser().getSignOutUrl());
        return  permitUrl.toArray(new String[permitUrl.size()]);
    }

}
