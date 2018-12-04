package indi.qiaolin.security.core.social.wx.connect;

import indi.qiaolin.security.core.social.wx.api.WeiXin;
import indi.qiaolin.security.core.social.wx.api.WeiXinUserInfo;
import lombok.NoArgsConstructor;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author qiaolin
 * @version 2018/12/3
 **/

@NoArgsConstructor
public class WeiXinAdapter implements ApiAdapter<WeiXin> {

    private String openId;

    public WeiXinAdapter(String openId){
        this.openId = openId;
    }


    @Override
    public boolean test(WeiXin api) {
        return true;
    }

    @Override
    public void setConnectionValues(WeiXin api, ConnectionValues values) {
        WeiXinUserInfo userInfo = api.getUserInfo(openId);
        values.setDisplayName(userInfo.getNickname());
        values.setProviderUserId(userInfo.getOpenid());
        values.setImageUrl(userInfo.getHeadimgurl());
    }

    @Override
    public UserProfile fetchUserProfile(WeiXin api) {
        return null;
    }

    @Override
    public void updateStatus(WeiXin api, String message) {

    }
}
