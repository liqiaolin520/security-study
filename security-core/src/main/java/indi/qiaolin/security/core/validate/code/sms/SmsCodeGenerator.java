package indi.qiaolin.security.core.validate.code.sms;

import indi.qiaolin.security.core.property.SecurityProperties;
import indi.qiaolin.security.core.validate.code.ValidateCode;
import indi.qiaolin.security.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author qiaolin
 * @version 2018/11/28
 **/
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 验证码生成
     * @param request HttpServletRequest包装类
     * @return
     */
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code, securityProperties.getCode().getSms().getExpireTime());
    }

}
