package indi.qiaolin.security.app.controller;

import indi.qiaolin.security.app.social.AppSignUpUtils;
import indi.qiaolin.security.core.social.support.SocialUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qiaolin
 * @version 2018/12/7
 **/


@RestController
public class AppSecretController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private AppSignUpUtils appSignUpUtils;


    /**
     * 不使用这个中跳转的方案，app或者前后端分离不支持
     * @param request
     * @return
     */
    @RequestMapping("/social/signUp")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SocialUserInfo socialInfo(HttpServletRequest request){
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        SocialUserInfo socialUserInfo = new SocialUserInfo();
        ConnectionKey key = connection.getKey();
        socialUserInfo.setProviderId(key.getProviderId());
        socialUserInfo.setProviderUserId(key.getProviderUserId());
        socialUserInfo.setNickName(connection.getDisplayName());
        socialUserInfo.getHeadImg();
        appSignUpUtils.saveConnectionData(new ServletWebRequest(request), connection.createData());
        return socialUserInfo;

    }


}
