package indi.qiaolin.security.core.property;

import lombok.Getter;
import lombok.Setter;

/**
 * session 配置
 * @author qiaolin
 * @version 2018/12/5
 **/


@Setter
public class SessionProperties {

    /** 同一个用户最大的在线人数 */
    @Getter
    private int maximumSession = 1;

    /** 达到最大人数时是否阻止新的登陆请求，默认为false,不阻止，新的登陆会将老的登陆挤掉 */
    private boolean maxSessionPreventsLogin;

    /** session失效时跳转的地址 */
    @Getter
    private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;


    public boolean getMaxSessionPreventsLogin() {
        return maxSessionPreventsLogin;
    }


}



