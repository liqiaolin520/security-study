package indi.qiaolin.security.app;

import indi.qiaolin.security.app.jwt.MyJwtTokenEnhancer;
import indi.qiaolin.security.core.property.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author qiaolin
 * @version 2018/12/7
 **/

@Configuration
public class TokenConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    @ConditionalOnProperty(prefix = "qiao.security.oauth2", name = "token-store", havingValue = "redis")
    public TokenStore tokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }


    @Configuration
    @ConditionalOnProperty(prefix = "qiao.security.oauth2", name = "token-store", havingValue = "jwt", matchIfMissing = true)
    public static class JwtConfig{

        @Autowired
        private SecurityProperties securityProperties;

        /** token 存储方式 */
        @Bean
        public TokenStore tokenStore(){
            return new JwtTokenStore(jwtAccessTokenConverter());
        }


        /** jwt 令牌解析器 */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
            return jwtAccessTokenConverter;
        }

        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer(){
            return new MyJwtTokenEnhancer();
        }

    }

}
