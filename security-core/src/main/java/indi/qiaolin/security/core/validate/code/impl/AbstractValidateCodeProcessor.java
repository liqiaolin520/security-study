package indi.qiaolin.security.core.validate.code.impl;

import indi.qiaolin.security.core.property.SecurityConstants;
import indi.qiaolin.security.core.validate.code.*;
import indi.qiaolin.security.core.validate.code.image.ImageCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author qiaolin
 * @version 2018/11/28
 **/

public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor{

    private static final String GENERATOR_SUFFIX = "CodeGenerator";

    /** session策略类，用于操作session */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /** spring会将所有的ValidateCodeGenerator收集起来 以名字为Key，本身为值 */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGeneratorMap;

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
        sessionStrategy.setAttribute(request, getSessionKey(), validateCode);
    }

    /**
     * 发送验证码，
     * 例如：
     *      图形验证码写出到前端
     *      短信验证码发送给用户
     * @param request
     * @param validateCode 生成的验证码
     */
    protected abstract void send(ServletWebRequest request, T validateCode) throws IOException, Exception;




    @Override
    public void validate(ServletWebRequest request) {

        String codeInRequest = null;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
        } catch (ServletRequestBindingException e) {
            throw  new ValidateCodeException("获取验证码的值失败！");
        }
        ValidateCodeType validateType = getValidateType();


        if(StringUtils.isBlank(codeInRequest)){
            throw new ValidateCodeException("验证码不能为空！");
        }

        String sessionKey = getSessionKey();
        T codeInSession = (T) sessionStrategy.getAttribute(request, sessionKey);
        if(codeInSession == null){
            throw new ValidateCodeException("验证码不存在！");
        }

        if(codeInSession.isExpire()){
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateCodeException("验证码已过期！");
        }

        if(!StringUtils.equals(codeInRequest, codeInSession.getCode())){
            throw new ValidateCodeException("验证码不匹配！");
        }

        sessionStrategy.removeAttribute(request, sessionKey);
    }

    /**
     *  根据当前处理的类的前缀获取验证码类型
     * @return
     */
    private ValidateCodeType getValidateType(){
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    /**
     *  获取验证码存放到Session中的名称
     * @return
     */
    private String getSessionKey(){
        return SESSION_KEY_PREFIX + getValidateType().toString();
    }

}
