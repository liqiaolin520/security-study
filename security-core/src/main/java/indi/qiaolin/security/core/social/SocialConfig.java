package indi.qiaolin.security.core.social;

import indi.qiaolin.security.core.property.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;

import javax.sql.DataSource;

/**
 * @author qiaolin
 * @version 2018/12/2
 **/

@Configuration
@EnableSocial // 开启第三方登陆
public class SocialConfig extends SocialConfigurerAdapter{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required =  false)
    private ConnectionSignUp connectionSignUp;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        jdbcUsersConnectionRepository.setTablePrefix("qiao_");
        jdbcUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
        return jdbcUsersConnectionRepository;
    }

    //@Bean
    //public SpringSocialConfigurer springSocialConfigurer(){
    //    return new SpringSocialConfigurer();
    //}

    @Bean
    public MySpringSocialConfigurer springSocialConfigurer(){
        MySpringSocialConfigurer springSocialConfigurer = new MySpringSocialConfigurer(securityProperties.getSocial().getFilterProcessUrl());
        // 设置注册页面
        springSocialConfigurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        return springSocialConfigurer;
    }

    /**
     * spring social第三方登陆后会去利用访问数据库的类（参见 JdbcUsersConnectionRepository）去查询有没有这个用户存在, 如果不存在
     * 就会抛出异常，然后他的处理方式是检查用户有没有配置注册页面或接口，如果有就跳过去，但是在跳过去之前会将用户授权信息、以及像服务提供接口获取
     * 到的 accessToken ，以及用户的基本信息放入到session中，当我们从注册页面跳转回来的时候，我们可以用这个类获取到之前的授权信息等
     * @see  org.springframework.social.security.SocialAuthenticationFilter:335
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }

}
