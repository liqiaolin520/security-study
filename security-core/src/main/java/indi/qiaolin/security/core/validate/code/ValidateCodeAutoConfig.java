package indi.qiaolin.security.core.validate.code;

import indi.qiaolin.security.core.validate.code.image.ImageCodeGenerator;
import indi.qiaolin.security.core.validate.code.image.ImageCodeProcessor;
import indi.qiaolin.security.core.validate.code.sms.SmsCodeGenerator;
import indi.qiaolin.security.core.validate.code.sms.SmsCodeProcessor;
import indi.qiaolin.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码自动配置类
 * @author qiaolin
 * @version 2018/11/27
 **/


@Configuration
public class ValidateCodeAutoConfig {

    /** 图形验证码生成器 */
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator(){
        return new ImageCodeGenerator();
    }

    /** 短信验证码生成器 */
    @Bean
    @ConditionalOnMissingBean(value = SmsCodeGenerator.class)
    public ValidateCodeGenerator smsCodeGenerator(){
        return new SmsCodeGenerator();
    }

    /** 图形验证码流程处理器 */
    @Bean
    public ValidateCodeProcessor imageValidateCodeProcessor(){
        return new ImageCodeProcessor();
    }

    /** 短信验证码流程处理类 */
    @Bean
    public ValidateCodeProcessor smsValidateCodeProcessor(){
        return new SmsCodeProcessor();
    }

    /** 短信发送器 */
    @Bean
    public SmsCodeSender smsCodeSender(){
        return new SmsCodeSender();
    }

}
