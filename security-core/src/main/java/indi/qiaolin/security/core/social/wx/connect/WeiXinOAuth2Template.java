package indi.qiaolin.security.core.social.wx.connect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 完成微信的OAuth2认证流程模板类，国内厂商实现的OAuth2每个都不同，
 * spring默认的OAuth2Template适应不了，只能针对每个厂商自己微调
 * @author qiaolin
 * @version 2018/12/3
 **/

@Slf4j
public class WeiXinOAuth2Template extends OAuth2Template{
    /** 微信刷新Token的地址 */
    private static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";


    /** 客户端Id，也就是申请的微信appId */
    private String clientId;

    private String clientSecret;

    private String accessTokenUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    public WeiXinOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
    }

    /**
     *  以授权码交换令牌
     * @param authorizationCode
     * @param redirectUri
     * @param additionalParameters
     * @return
     */
    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
        StringBuilder sb = new StringBuilder(accessTokenUrl);
        sb.append("?appid=").append(clientId);
        sb.append("&secret=").append(clientSecret);
        sb.append("&code=").append(authorizationCode);
        sb.append("&grant_type=authorization_code");
        sb.append("&redirect_url=").append(redirectUri);
        return getAccessGrant(sb.toString());
    }


    private AccessGrant getAccessGrant(String accessTokenRequestUrl){
        log.info("获取accessToken，请求URL：{}", accessTokenRequestUrl);
        String result = getRestTemplate().getForObject(accessTokenRequestUrl, String.class);
        log.info("获取accessToken，响应内容：{}", result);

        Map<String, Object> map = null;

        try {
            map = objectMapper.readValue(result, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("解析微信AccessToken信息出错！");
        }

        if(map.get("access_token") == null){
            throw new RuntimeException("获取微信AccessToken信息失败！");
        }

        // 这里因为用户授权时直接拿到了openId，不像QQ一样，需要使用授权码换取openId
        WeiXinAccessGrant accessGrant = new WeiXinAccessGrant(
                MapUtils.getString(map, "access_token"),
                MapUtils.getString(map, "scope"),
                MapUtils.getString(map, "refresh_token"),
                MapUtils.getLong(map, "expires_in")
                );

        accessGrant.setOpenId(MapUtils.getString(map, "openid"));

        return accessGrant;
    }


    @Override
    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {

        StringBuilder sb = new StringBuilder(REFRESH_TOKEN_URL);
        sb.append("?appid=").append(clientId);
        sb.append("&grant_type=refresh_token");
        sb.append("&refresh_token=").append(refreshToken);

        return getAccessGrant(sb.toString());
    }

    /**
     *  增加特定的参数
     *  构建获取授权码的请求，也就是引导用户跳转到微信的地址
     * @param parameters
     * @return
     */
    @Override
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        String url = super.buildAuthenticateUrl(parameters);
        return url + "&appid=" + clientId + "&scope=snsapi_login";
    }

    @Override
    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return buildAuthenticateUrl(parameters);
    }

    /**
     *  微信返回的contentType是html/text，添加对应的HttpMessageConverter来处理
     * @return
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
