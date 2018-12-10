package indi.qiaolin.security.core.property;

import lombok.Getter;
import lombok.Setter;

/**
 * OAuth2 客户凭证配置
 * @author qiaolin
 * @version 2018/12/7
 **/

@Getter
@Setter
public class OAuth2ClientProperties {

    /** 客户Id */
    private String clientId;

    /** 客户凭证 */
    private String clientSecret;

    /** 客户token过期秒数 */
    private int accessTokenValiditySeconds;

}
