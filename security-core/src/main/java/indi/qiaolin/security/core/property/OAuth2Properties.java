package indi.qiaolin.security.core.property;

import lombok.Getter;
import lombok.Setter;

/**
 * OAuth2 认证流程配置
 * @author qiaolin
 * @version 2018/12/7
 **/

@Getter
@Setter
public class OAuth2Properties {

    /** JWT 签名 */
    private String jwtSigningKey = "qiao";

    /** tokenStore 使用的类型 */
    private TokenStoreType tokenStore ;

    /** 客户端配置 */
    private OAuth2ClientProperties[] clients = {};

}
