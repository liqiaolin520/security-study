package indi.qiaolin.security.core.validate.code.impl;

import indi.qiaolin.security.core.validate.code.ValidateCode;
import indi.qiaolin.security.core.validate.code.ValidateCodeGenerator;
import indi.qiaolin.security.core.validate.code.ValidateCodeProcessor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author qiaolin
 * @version 2018/11/28
 **/

public abstract class AbstractValidateCodeProcessor<T> implements ValidateCodeProcessor{

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
        String type = getProcessorType(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(type + GENERATOR_SUFFIX);
        return (T) validateCodeGenerator.generate(request);
    }


    /**
     * 保存验证码，可以将验证放入到 session中或者redis把。
     * @param request
     * @param validateCode  生成的验证码
     */
    protected void save(ServletWebRequest request, T validateCode) {
        sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase(), validateCode);
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

    /**
     * 获取请求连接的/code/后的类型
     * @param request
     * @return
     */
    private String getProcessorType(ServletWebRequest request) {
        String requestURI = request.getRequest().getRequestURI();
        return StringUtils.substringAfter(requestURI, "/code/");
    }

}
