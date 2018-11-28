package indi.qiaolin.security.core.validate.code.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * 短信发送器，模拟
 * @author qiaolin
 * @version 2018/11/28
 **/

@Slf4j
public class SmsCodeSender {

    /**
     * 验证码发送(模拟)
     * @param mobile
     * @param code
     */
    public void send(String mobile, String code){
        log.debug("发送验证码，验证码为{}", code);
    }

}
