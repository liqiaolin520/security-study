package indi.qiaolin.security.app;

import indi.qiaolin.security.core.property.OAuth2ClientProperties;
import indi.qiaolin.security.core.property.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiaolin
 * @version 2018/12/6
 **/

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore tokenStore;

    @Autowired(required =  false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    /**
     *  用于配置端点
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore);
        if(jwtAccessTokenConverter != null){
            TokenEnhancerChain chain = new TokenEnhancerChain();
            List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
            tokenEnhancers.add(jwtTokenEnhancer);
            tokenEnhancers.add(jwtAccessTokenConverter);
            chain.setTokenEnhancers(tokenEnhancers);

            endpoints.tokenEnhancer(chain);
            endpoints.accessTokenConverter(jwtAccessTokenConverter);
        }
    }

    /**
     * 配置客户端相关
     * @param clientConfig
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clientConfig) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clientConfig.inMemory();
        OAuth2ClientProperties[] clients = securityProperties.getOauth2().getClients();
        if(ArrayUtils.isNotEmpty(clients)){
            for (OAuth2ClientProperties client : clients) {
                builder.withClient(client.getClientId())
                        .secret(client.getClientSecret())
                        .accessTokenValiditySeconds(client.getAccessTokenValiditySeconds())
                        .scopes("all", "read")
                        .authorizedGrantTypes("refresh_token", "password");
            }
        }
    }


}
