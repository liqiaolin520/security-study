package indi.qiaolin.security.core.property;

/**
 *
 * 安全模块常量
 * @author qiaolin
 * @version 2018/11/29
 **/

public interface SecurityConstants {

    /** 默认的用户名密码登陆请求处理的Url */
    String DEFAULT_AUTHENTICATION_URL_FORM = "/login";

    /** 默认的手机登陆请求处理的Url */
    String DEFAULT_AUTHENTICATION_URL_MOBILE = "/login/mobile";

    /** 当请求需要身份认证时，会跳向这个地址 */
    String DEFAULT_UN_AUTHENTICATION_URL = "/authentication/require";


    /** 默认的验证码处理路径前缀 */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";
    /** 默认的登录页 */
    String DEFAULT_LOGIN_PAGE = "/demo-login.html";

    /** 图形验证码验证时， http请求中默认携带验证码信息的参数名称 */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";

    /** 短信验证码验证时， http请求中默认携带验证码信息的参数名称 */
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

    /** 发送短信验证码 或者 验证短信信息时，传递手机号的参数名称 */
    String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    /** 图形验证码的格式 */
    String DEFAULT_IMAGE_CODE_FORMAT = "JPEG";

    /** 默认session失效时跳转的页面 */
    String DEFAULT_SESSION_INVALID_URL = "/session/invalid";

}
