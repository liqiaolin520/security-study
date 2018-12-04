package indi.qiaolin.security.core.property;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qiaolin
 * @version 2018/12/2
 **/

@Getter
@Setter
public class SocialProperties {

    /** 社交登陆拦截的前缀 */
    private String filterProcessUrl = "/auth";

    /** QQ登陆配置 */
    private QQProperties qq = new QQProperties();

    /** 微信登陆配置 */
    private WeiXinProperties wx = new WeiXinProperties();
}
