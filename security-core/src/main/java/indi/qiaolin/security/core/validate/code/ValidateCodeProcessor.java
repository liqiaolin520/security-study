package indi.qiaolin.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证处理器， 封装不同的校验逻辑
 * @author qiaolin
 * @version 2018/11/28
 **/

public interface ValidateCodeProcessor {

    /** 验证码存放到Session中的前缀 */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     *  创建验证码
      * @param request
     */
    void create(ServletWebRequest request) throws Exception;

}
