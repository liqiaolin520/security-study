package indi.qiaolin.security.app.validate.code.impl;

import indi.qiaolin.security.core.validate.code.ValidateCode;
import indi.qiaolin.security.core.validate.code.ValidateCodeRepository;
import indi.qiaolin.security.core.validate.code.ValidateCodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * 基于redis得验证码存储
 * @author qiaolin
 * @version 2018/12/6
 **/

@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeType type) {
        redisTemplate.opsForValue().set(buildKey(request, type), validateCode, 30, TimeUnit.MINUTES);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        return (ValidateCode) redisTemplate.opsForValue().get(buildKey(request, type));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        redisTemplate.delete(buildKey(request, type));
    }


    private String buildKey(ServletWebRequest request, ValidateCodeType type){
        String deviceId = request.getHeader("deviceId");
        return "code:" + type.name() + ":" + deviceId;
    }

}
