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
public class ImageCodeProperties extends  SmsCodeProperties{

    /** 验证码宽度 */
    private int width = 67;

    /** 验证码高度 */
    private int height = 23;

    /** 图形验证码默认长度为4 */
    public ImageCodeProperties(){
        setLength(4);
    }

}
