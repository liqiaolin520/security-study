package indi.qiaolin.security.core.social.wx.connect;

import indi.qiaolin.security.core.social.wx.api.WeiXin;
import indi.qiaolin.security.core.social.wx.api.WeiXinImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 *
 * 微信的OAuth2流程处理器的提供者，供spring social的 connect 体系调用
 * @author qiaolin
 * @version 2018/12/3
 **/
public class WeiXinServiceProvider extends AbstractOAuth2ServiceProvider<WeiXin> {

    /**
     * 微信获取授权码的url
     */
    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
    /**
     * 微信获取accessToken的url
     */
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";


    public WeiXinServiceProvider(String appId, String appSecret) {
        super(new WeiXinOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
    }

    /**
     *  获取微信API
     * @param accessToken
     * @return
     */
    @Override
    public WeiXin getApi(String accessToken) {
        return new WeiXinImpl(accessToken);
    }

}
