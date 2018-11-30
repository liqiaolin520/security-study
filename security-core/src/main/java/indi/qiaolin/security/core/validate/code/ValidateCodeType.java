package indi.qiaolin.security.core.validate.code;

import indi.qiaolin.security.core.property.SecurityConstants;

/**
 *
 * 验证类型
 * @author qiaolin
 * @version 2018/11/29
 **/

public enum  ValidateCodeType {

    /** 短信验证码 */
    SMS {

       @Override
       public String getParameterOnValidate(){
           return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },

    /** 图形验证码 */
    IMAGE {
        @Override
        public String getParameterOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    public abstract String getParameterOnValidate();

}
