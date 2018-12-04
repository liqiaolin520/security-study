package indi.qiaolin.security.core.social.qq.connet;

import indi.qiaolin.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author qiaolin
 * @version 2018/12/2
 **/
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    /**
     * @param providerId 提供者Id
     * @param appId
     * @param appSecret
     */
    public QQConnectionFactory(String providerId, String appId, String appSecret) {

        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}
