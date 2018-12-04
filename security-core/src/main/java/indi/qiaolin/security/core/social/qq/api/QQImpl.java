package indi.qiaolin.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * @author qiaolin
 * @version 2018/12/2
 **/

@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    /** 获取用户openId的接口  */
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /** 获取用户个人信息的接口 */
    private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    /** 用户的开放Id, 通过上面的接口获得 */
    private String openId;

    /** 在QQ互联上申请的Id */
    private String appId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId){
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        log.info("获取到用户OpenId信息数据：{}", result);
        this.openId = StringUtils.substringBetween(result,"\"openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getQQUserInfo() {
        String url = String.format(URL_GET_USER_INFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        log.info("获取到用户个人信息：{}", result);
        try {
            QQUserInfo qqUserInfo = objectMapper.readValue(result, QQUserInfo.class);
            qqUserInfo.setOpenId(openId);
            return qqUserInfo;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("无法获取个人信息！", e);
        }
    }

}
