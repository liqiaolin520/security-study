package indi.qiaolin.security.core.social.qq.connet;

import indi.qiaolin.security.core.social.qq.api.QQ;
import indi.qiaolin.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author qiaolin
 * @version 2018/12/2
 **/
public class QQAdapter implements ApiAdapter<QQ> {
    /**
     *  api服务是否可用
     * @param api
     * @return
     */
    @Override
    public boolean test(QQ api) {
        return true;
    }


    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo qqUserInfo = api.getQQUserInfo();
        values.setDisplayName(qqUserInfo.getNickname());
        values.setImageUrl(qqUserInfo.getFigureurl_qq_1());
        // qq 没有这个东西，主页url
        values.setProfileUrl(null);
        values.setProviderUserId(qqUserInfo.getOpenId());
    }

    /**
     * 绑定与解绑
     * @param api
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    /**
     * 更新状态什么的
     * @param api
     * @param message
     */
    @Override
    public void updateStatus(QQ api, String message) {

    }
}
