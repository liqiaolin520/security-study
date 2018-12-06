package indi.qiaolin.security;

import indi.qiaolin.security.core.authentication.AbstractChannelSecurityConfig;
import indi.qiaolin.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import indi.qiaolin.security.core.property.SecurityConstants;
import indi.qiaolin.security.core.property.SecurityProperties;
import indi.qiaolin.security.core.validate.code.ValidateCodeSecurityConfig;
import indi.qiaolin.security.session.DefaultExpiredSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qiaolin
 * @version 2018/11/20
 **/

@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig{

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    /** spring social配置 */
    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    /**
     *  这个方法可以配置
     *      1、登陆的方式
     *      2、接口的访问策咯，如哪些接口可以直接访问，哪些接口需要认证后才能访问
     *      3、登陆页面地址
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        applyPasswordAuthenticationConfig(http);

        http
            //在usernamePassword过滤器之前加入验证码过滤器
            .apply(validateCodeSecurityConfig)
            .and()
            // 短信登陆认证配置
            .apply(smsCodeAuthenticationSecurityConfig)
            .and()
            // 开启 spring social
            .apply(springSocialConfigurer)
            .and()
            // 开启记住我功能
            .rememberMe()
            // 记住我持久化的操作对象
            .tokenRepository(persistentTokenRepository())
            // 过期时间
            .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
            .userDetailsService(userDetailsService)

            .and()
            // session 管理
            .sessionManagement()
            // session过期后跳转的地址
            //.invalidSessionUrl("/session/invalid")
            .invalidSessionStrategy(invalidSessionStrategy)

            // 同一个账号最大的session数量
            .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSession())
            //  配置登陆被挤下线的用户session策咯，也就给用户明白一点的提示把
            .expiredSessionStrategy(sessionInformationExpiredStrategy)
            // 配置登陆被挤下线的用户URL地址，
            //.expiredUrl("xxxx")

            // 配置到了最大的session数量就不允许登陆了
            .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().getMaxSessionPreventsLogin())

            // 这里需要两个and
            .and()
            .and()

            .logout()
            // 设置退出登录地址
            .logoutUrl("/signOut")
            // 退出成功处理器
            .logoutSuccessHandler(logoutSuccessHandler)
            .and()

            //  认证请求
            .authorizeRequests()
            //这些请求，可以匿名访问
            .antMatchers(buildPermitUrl()).permitAll()
            // 剩下的全部得请求，都需要认证，才能访问
            .anyRequest().authenticated()

            // 关闭 csrf保护
            .and().csrf().disable();
    }


    /**
     * token 存放到数据库的操作类
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 建立cookie记录表，
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
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
