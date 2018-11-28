package indi.qiaolin.security.core.property;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

/**
 * 手机验证码配置类
 * @author qiaolin
 * @version 2018/11/28
 **/

@Getter
@Setter
public class SmsCodeProperties {


    /** 验证码长度 */
    private int length = 6;

    /** 验证码失效时间(秒) */
    private int expireTime = 60;

    /** 拦截的url，以,分割 */
    private String url = StringUtils.EMPTY;
}
