package indi.qiaolin.security.core.property;

/**
 * @author qiaolin
 * @version 2018/12/10
 **/
public enum TokenStoreType {

    /** redis 管理负责token的存储 */
    REDIS,

    /** jwt 方式服务token的存储 */
    JWT;

}
