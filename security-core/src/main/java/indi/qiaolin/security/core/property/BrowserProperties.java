package indi.qiaolin.security.core.property;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qiaolin
 * @version 2018/11/26
 **/


@Getter
@Setter
public class BrowserProperties {

    /** 登陆页面 */
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE;

    /** 注册页面 */
    private String signUpUrl = "/demo-register.html";

    /** 退出后跳转的页面 */
    private String signOutUrl;

    /** 登陆类型 */
    private LoginType loginType = LoginType.JSON;

    /** 记住我的持续时间 */
    private int rememberMeSeconds = 3600;

    /** session 配置 */
    private SessionProperties session = new SessionProperties();

}
