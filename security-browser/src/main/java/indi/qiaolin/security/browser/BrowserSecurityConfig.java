package indi.qiaolin.security.browser;

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


            .and()
            // 并且 认证请求
            .authorizeRequests()



            // 全部得请求
            .anyRequest()
            // 都需要认证，才能访问
            .authenticated();

    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
