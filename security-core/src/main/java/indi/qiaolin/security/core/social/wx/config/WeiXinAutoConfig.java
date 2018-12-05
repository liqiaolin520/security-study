package indi.qiaolin.security.core.social.wx.config;

import indi.qiaolin.security.core.property.SecurityProperties;
import indi.qiaolin.security.core.property.WeiXinProperties;
import indi.qiaolin.security.core.social.MyConnectView;
import indi.qiaolin.security.core.social.wx.connect.WeiXinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 *
 * 微信自动配置类
 * @author qiaolin
 * @version 2018/12/4
 **/

@Configuration
@ConditionalOnProperty(prefix = "qiao.security.social.wx", name = "app-id")
public class WeiXinAutoConfig extends SocialAutoConfigurerAdapter{

    @Autowired
    private SecurityProperties securityProperties;

    /**
     *  微信连接工厂
     * @return
     */
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeiXinProperties wx = securityProperties.getSocial().getWx();
        return new WeiXinConnectionFactory(wx.getProviderId(), wx.getAppId(), wx.getAppSecret());
    }
//                                /connect/wx
    @Bean({"connect/wxConnected", "connect/wx", "/connect/wx", "connect/wxConnect", "/connect/wxConnect", "/connect/wx.html", "connect/wx.html"})
    public MyConnectView connectView(){
        return new MyConnectView();
    }

}
