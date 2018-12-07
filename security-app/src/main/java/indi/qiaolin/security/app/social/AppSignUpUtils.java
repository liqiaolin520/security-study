package indi.qiaolin.security.app.social;

import indi.qiaolin.security.app.exception.AppSecretException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;

/**
 * app第三方登陆后帮助注册的工具类
 * 该类会将connectionData放入到Redis中
 * @author qiaolin
 * @version 2018/12/7
 **/

@Component
public class AppSignUpUtils {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    /**
     *  将连接信息保存至Redis
     * @param request
     * @param connectionData
     */
    public void saveConnectionData(WebRequest request, ConnectionData connectionData){
        redisTemplate.opsForValue().set(getKey(request), connectionData, 10 , TimeUnit.MINUTES);
    }


    public void doPostSignUp(WebRequest request, String userId){
        if(!redisTemplate.hasKey(getKey(request))){
            throw new AppSecretException("无法找到用户缓存的社交账号信息");
        }

        ConnectionData connectionData = (ConnectionData) redisTemplate.opsForValue().get(getKey(request));

        Connection<?> connection = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId()).createConnection(connectionData);

        usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);

        redisTemplate.delete(getKey(request));
    }

    /**
     *  获取Key
     * @param request
     * @return
     */
    private String getKey(WebRequest request) {
        String deviceId = request.getHeader("deviceId");
        return "security:social.connect." + deviceId;
    }

    ;


}
