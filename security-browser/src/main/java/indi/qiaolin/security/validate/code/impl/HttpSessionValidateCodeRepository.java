package indi.qiaolin.security.validate.code.impl;

import indi.qiaolin.security.core.validate.code.ValidateCode;
import indi.qiaolin.security.core.validate.code.ValidateCodeRepository;
import indi.qiaolin.security.core.validate.code.ValidateCodeType;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author qiaolin
 * @version 2018/12/6
 **/

@Component
public class HttpSessionValidateCodeRepository implements ValidateCodeRepository{

    /** 验证码存放到Session中的前缀 */
    private static final String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    public void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeType type) {
        sessionStrategy.setAttribute(request, buildKey(type), validateCode);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        return (ValidateCode) sessionStrategy.getAttribute(request, buildKey(type));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        sessionStrategy.removeAttribute(request, buildKey(type));
    }

    /**
     * 构建存入到session中验证码的Key
     * @param type
     * @return
     */
    private String buildKey(ValidateCodeType type){
        return SESSION_KEY_PREFIX + type.name().toUpperCase();
    }
}
