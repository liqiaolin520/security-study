package indi.qiaolin.security.app.social.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * openId认证配置类
 * @author qiaolin
 * @version 2018/12/7
 **/

@Component
public class OpenIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private SocialUserDetailsService socialUserDetailsService;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        OpenIdAuthenticationFilter filter = new OpenIdAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        OpenIdAuthenticationProvider provider = new OpenIdAuthenticationProvider();
        provider.setSocialUserDetailsService(socialUserDetailsService);
        provider.setUsersConnectionRepository(usersConnectionRepository);

        http.authenticationProvider(provider)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);


    }




}
