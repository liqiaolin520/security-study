package indi.qiaolin.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author qiaolin
 * @version 2018/11/29
 **/

@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessorMap;


    /**
     * 根据类型获取验证码
     * @param type 验证码类型
     * @return
     */
    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type){
        return findValidateCodeProcessor(type.name());
    }

    /**
     *  根据类型获取验证码处理器
     * @param type 验证码类型
     * @return
     */
    public ValidateCodeProcessor findValidateCodeProcessor(String type){
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor validateCodeProcessor = validateCodeProcessorMap.get(name+1);
        if(validateCodeProcessor == null){
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }
        return validateCodeProcessor;
    }

}
