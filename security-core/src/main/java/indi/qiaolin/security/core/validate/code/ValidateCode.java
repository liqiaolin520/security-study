package indi.qiaolin.security.core.validate.code;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 验证码类
 *  手机验证码可以直接用
 * @author qiaolin
 * @version 2018/11/28
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCode {

    /** 验证码 */
    private String code;

    /** 过期时间 */
    private LocalDateTime expireTime;

    /**
     * @param code 验证码
     * @param expireTime 从现在开始多少秒过期
     */
    public ValidateCode(String code, int expireTime){
        this(code, LocalDateTime.now().plusSeconds(expireTime));
    }

    /**
     * 验证码是否过期
     * @return
     */
    public boolean isExpire(){
        return LocalDateTime.now().isAfter(expireTime);
    }

}
