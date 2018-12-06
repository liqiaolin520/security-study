package indi.qiaolin.security.core.validate.code.impl;

import indi.qiaolin.security.core.validate.code.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 *
 * @author qiaolin
 * @version 2018/11/28
 **/

public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor{

    private static final String GENERATOR_SUFFIX = "CodeGenerator";

    /** spring会将所有的ValidateCodeGenerator收集起来 以名字为Key，本身为值 */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGeneratorMap;

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        T validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     *  生成验证码
     * @param request
     * @return  验证码对象
     */
    protected T generate(ServletWebRequest request) {
        String type = getValidateType().toString().toLowerCase();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(type + GENERATOR_SUFFIX);
        return (T) validateCodeGenerator.generate(request);
    }


    /**
     * 保存验证码，可以将验证放入到 session中或者redis把。
     * @param request
     * @param validateCode  生成的验证码
     */
    protected void save(ServletWebRequest request, T validateCode) {
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, code, getValidateType());
    }

    /**
     * 发送验证码，
     * 例如：
     *      图形验证码写出到前端
     *      短信验证码发送给用户
     * @param request
     * @param validateCode 生成的验证码
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, T validateCode) throws Exception;


    @Override
    public void validate(ServletWebRequest request) {

        String codeInRequest = null;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), getValidateType().getParameterOnValidate());
        } catch (ServletRequestBindingException e) {
            throw  new ValidateCodeException("获取验证码的值失败！");
        }
        ValidateCodeType validateType = getValidateType();


        if(StringUtils.isBlank(codeInRequest)){
            throw new ValidateCodeException("验证码不能为空！");
        }

        T codeInSession = (T) validateCodeRepository.get(request, validateType);
        if(codeInSession == null){
            throw new ValidateCodeException("验证码不存在！");
        }

        if(codeInSession.isExpire()){
            validateCodeRepository.remove(request, validateType);
            throw new ValidateCodeException("验证码已过期！");
        }

        if(!StringUtils.equals(codeInRequest, codeInSession.getCode())){
            throw new ValidateCodeException("验证码不匹配！");
        }

        validateCodeRepository.remove(request, validateType);
    }

    /**
     *  根据当前处理的类的前缀获取验证码类型
     * @return
     */
    private ValidateCodeType getValidateType(){
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

}
