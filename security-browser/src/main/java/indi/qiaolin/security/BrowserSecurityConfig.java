package indi.qiaolin.security;

import com.sun.org.apache.xpath.internal.operations.And;
import indi.qiaolin.security.authentication.MyAuthenticationFailureHandler;
import indi.qiaolin.security.authentication.MyAuthenticationSuccessHandler;
import indi.qiaolin.security.core.property.SecurityProperties;
import indi.qiaolin.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiaolin
 * @version 2018/11/20
 **/

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SecurityProperties securityProperties;




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

        // 验证码过滤器
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter(authenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        // 再usernamePassword过滤器之前加入验证码过滤器
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)

            // 登陆方式为form表单，spring security会有一个默认表单
            .formLogin()

            // 自定义登陆页面, 可以是个接口，接口中配置相关逻辑，例如：
            // 如果用户的请求是以.html结尾的那么我们重定向到登陆页面，如果不是则返回json信息
            .loginPage("/authentication/require")

            // 配置了自定义登陆页面，这个一定要自己重新配置一下，否则没有login这个登陆接口
            // 这个是处理登陆请求流程的地址，即认证的地址
            .loginProcessingUrl("/login")

            // 认证成功处理器
            .successHandler(authenticationSuccessHandler)

            // 认证失败处理器
            .failureHandler(authenticationFailureHandler)

            .and()
            // 并且 认证请求
            .authorizeRequests()

            .antMatchers(buildPermitUrl()).permitAll()

            // 全部得请求
            .anyRequest()
            // 都需要认证，才能访问
            .authenticated()

            // 关闭 csrf保护
            .and().csrf().disable();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    private String[] buildPermitUrl(){
        List<String> permitUrl = new ArrayList<>();

        permitUrl.add("/authentication/require");
        permitUrl.add(securityProperties.getBrowser().getLoginPage());
        permitUrl.add("/code/image");

        return  permitUrl.toArray(new String[permitUrl.size()]);
    }
}
