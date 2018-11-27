package indi.qiaolin.security.core.property;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

/**
 * 图形验证码配置类
 * @author qiaolin
 * @version 2018/11/27
 **/

@Getter
@Setter
public class ImageCodeProperties {

    /** 验证码宽度 */
    private int width = 67;

    /** 验证码高度 */
    private int height = 23;

    /** 验证码长度 */
    private int length = 4;

    /** 验证码失效时间(秒) */
    private int expireTime = 60;

    /** 拦截的url，以,分割 */
    private String url = StringUtils.EMPTY;
}
