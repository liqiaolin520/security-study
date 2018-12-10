package indi.qiaolin.security.app.jwt;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * JWT Token 增强器
 * @author qiaolin
 * @version 2018/12/10
 **/

public class MyJwtTokenEnhancer implements TokenEnhancer{

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
        Map<String, Object> info = new HashMap<>();
        info.put("company", "ascendant");

        defaultOAuth2AccessToken.setAdditionalInformation(info);

        return accessToken;
    }
}
