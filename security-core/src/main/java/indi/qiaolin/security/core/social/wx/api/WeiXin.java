package indi.qiaolin.security.core.social.wx.api;

/**
 * 微信Api调用接口
 * @author qiaolin
 * @version 2018/12/3
 **/
public interface WeiXin {

    /**
     *  获取微信用户信息
     * @param openId
     * @return
     */
    WeiXinUserInfo getUserInfo(String openId);

}
