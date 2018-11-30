package indi.qiaolin.security.core.validate.code.sms;

import indi.qiaolin.security.core.property.SecurityConstants;
import indi.qiaolin.security.core.validate.code.ValidateCode;
import indi.qiaolin.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * 短信验证码处理类
 * @author qiaolin
 * @version 2018/11/28
 **/

public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode>{

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws IOException, Exception {
        String mobile = ServletRequestUtils.getStringParameter(request.getRequest(), SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
        smsCodeSender.send(mobile, validateCode.getCode());
    }

}
