package indi.qiaolin.security.core.validate.code;

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

    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator(){
        return new ImageCodeGenerator();
    }

}
