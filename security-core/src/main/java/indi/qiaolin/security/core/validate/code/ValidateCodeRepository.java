package indi.qiaolin.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码持久化接口
 * @author qiaolin
 * @version 2018/12/6
 **/
public interface ValidateCodeRepository {

    /**
     * 验证码保存接口
     * @param request 包装类，包含HttpServletRequest与HttpServletResponse
     * @param validateCode 验证码
     * @param type 当前验证码的类型
     */
    void save(ServletWebRequest request, ValidateCode validateCode,ValidateCodeType type);

    /**
     *  验证码获取接口
     * @param request 包装类，包含HttpServletRequest与HttpServletResponse
     * @param type 当前验证码的类型
     * @return
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType type);

    /**
     *  删除验证码
     * @param request 包装类，包含HttpServletRequest与HttpServletResponse
     * @param type 当前验证码的类型
     */
    void remove(ServletWebRequest request, ValidateCodeType type);
}
