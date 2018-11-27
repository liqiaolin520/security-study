package indi.qiaolin.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 * @author qiaolin
 * @version 2018/11/26
 **/
public class ValidateCodeException extends AuthenticationException {


    public ValidateCodeException(String msg) {
        super(msg);
    }


}
