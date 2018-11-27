package indi.qiaolin.security.core.property;

import lombok.Getter;
import lombok.Setter;

/**
 * 验证码配置类，包括短信验证与图形码验证
 * @author qiaolin
 * @version 2018/11/27
 **/

@Getter
@Setter
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

}
