package indi.qiaolin.security.core.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * 微信登陆配置
 * @author qiaolin
 * @version 2018/12/3
 **/

@Getter
@Setter
public class WeiXinProperties extends SocialProperties {

    /** 提供者Id
     *  也是登陆Url的后缀
     * */
    private String providerId = "weixin";


}
