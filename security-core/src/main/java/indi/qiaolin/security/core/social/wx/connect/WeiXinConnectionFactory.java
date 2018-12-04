package indi.qiaolin.security.core.social.wx.connect;

import indi.qiaolin.security.core.social.wx.api.WeiXin;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * 微信连接工厂
 * @author qiaolin
 * @version 2018/12/3
 **/
public class WeiXinConnectionFactory extends OAuth2ConnectionFactory<WeiXin> {



    public WeiXinConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new WeiXinServiceProvider(appId, appSecret), new WeiXinAdapter());
    }

    /**
     *  由于微信的openId和accessToken是一起返回的，所在在这里直接根据accessToken设置providerUserId即可
     *  ，
     *  不用像QQ那样用QQAdapter来获取
     * @param accessGrant
     * @return
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if(accessGrant instanceof  WeiXinAccessGrant){
            return ((WeiXinAccessGrant) accessGrant).getOpenId();
        }
        return null;
    }

    /**
     *  创建OAuth2连接，因为创建连接时要去使用adapter获取信息， 获取信息需要使用到openId，
     *  但是微信不像QQ是标准的OAuth2协议，所以他在创建连接时就要将openId传过去
     * @param accessGrant
     * @return
     */
    @Override
    public Connection<WeiXin> createConnection(AccessGrant accessGrant) {
        String providerUserId = extractProviderUserId(accessGrant);
        return new OAuth2Connection<WeiXin>(
                    getProviderId(),
                    providerUserId,
                    accessGrant.getAccessToken(),
                    accessGrant.getRefreshToken(),
                    accessGrant.getExpireTime(),
                    getOAuth2ServiceProvider(),
                    getApiAdapter(providerUserId)
                );
    }


    /**
     *  创建OAuth2连接，因为创建连接时要去使用adapter获取信息， 获取信息需要使用到openId，
     *  但是微信不像QQ是标准的OAuth2协议，所以他在创建连接时就要将openId传过去
     * @param data
     * @return
     */
    @Override
    public Connection<WeiXin> createConnection(ConnectionData data) {
        return new OAuth2Connection<WeiXin>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    private ApiAdapter<WeiXin> getApiAdapter(String providerUserId) {
        return new WeiXinAdapter(providerUserId);
    }


    private OAuth2ServiceProvider<WeiXin> getOAuth2ServiceProvider(){
        return (OAuth2ServiceProvider<WeiXin>) getServiceProvider();
    }


}
