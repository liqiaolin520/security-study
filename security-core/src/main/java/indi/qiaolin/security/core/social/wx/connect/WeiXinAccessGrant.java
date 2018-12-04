package indi.qiaolin.security.core.social.wx.connect;

import lombok.Getter;
import lombok.Setter;
import org.springframework.social.oauth2.AccessGrant;

/**
 *  微信访问授权类，
 *  授权码换访问令牌时使用
 * @see  indi.qiaolin.security.core.social.wx.connect.WeiXinOAuth2Template#exchangeForAccess(java.lang.String, java.lang.String, org.springframework.util.MultiValueMap)
 * @author qiaolin
 * @version 2018/12/3
 **/

@Getter
@Setter
public class WeiXinAccessGrant extends AccessGrant{

    /** 用户开放ID */
    private String openId;


    public WeiXinAccessGrant(String accessToken) {
        super(accessToken);
    }

    public WeiXinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
    }
}
