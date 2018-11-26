package indi.qiaolin.security;

import com.sun.org.apache.xpath.internal.operations.And;
import indi.qiaolin.security.core.property.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author qiaolin
 * @version 2018/11/20
 **/

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{

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

        // 登陆方式为form表单，spring security会有一个默认表单
        http.formLogin()

            // 自定义登陆页面, 可以是个接口，接口中配置相关逻辑，例如：
            // 如果用户的请求是以.html结尾的那么我们重定向到登陆页面，如果不是则返回json信息
            .loginPage("/authentication/require")

            // 配置了自定义登陆页面，这个一定要自己重新配置一下，否则没有login这个登陆接口
            // 这个是处理登陆请求流程的地址，即认证的地址
            .loginProcessingUrl("/login")

            .and()
            // 并且 认证请求
            .authorizeRequests()

            .antMatchers(  "/authentication/require", securityProperties.getBrowser().getLoginPage()).permitAll()

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



}
