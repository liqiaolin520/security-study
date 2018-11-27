package indi.qiaolin.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 *
 * 验证码生成接口
 * @author qiaolin
 * @version 2018/11/27
 **/

public interface ValidateCodeGenerator {

    /**
     * 验证码生成
     * @param request HttpServletRequest包装类
     * @return
     */
    ImageCode generate(ServletWebRequest request);

}
