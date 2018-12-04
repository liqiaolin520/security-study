package indi.qiaolin.security.core.social.wx.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author qiaolin
 * @version 2018/12/3
 **/
public class WeiXinImpl extends AbstractOAuth2ApiBinding implements WeiXin {

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 获取用户信息的url
     */
    private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=";

    public WeiXinImpl(String accessToken){
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    @Override
    public WeiXinUserInfo getUserInfo(String openId) {
        String url = URL_GET_USER_INFO + openId;
        String result = getRestTemplate().getForObject(url, String.class);
        if(StringUtils.contains(result, "errCode")){
            return null;
        }
        WeiXinUserInfo weiXinUserInfo = null;
        try {
            weiXinUserInfo = objectMapper.readValue(result, WeiXinUserInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weiXinUserInfo;
    }


    /**
     * 重写这个方法是因为 微信响应的内容为UTF-8，而父类中这个方法加入的StringHttpMessageConverter的编码为默认编码 ISO-8859-1
     * @return
     */
    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        messageConverters.remove(0);
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return messageConverters;
    }
}
