package indi.qiaolin.security.core.validate.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author qiaolin
 * @version 2018/11/26
 **/


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageCode {

    /** 图片缓冲对象 */
    private BufferedImage bufferedImage;

    /** 验证码 */
    private String code;

    /** 过期时间 */
    private LocalDateTime expireTime;

    /**
     * @param bufferedImage 图片对象
     * @param code 验证码
     * @param expireTime 从现在开始多少秒过期
     */
    public ImageCode(BufferedImage bufferedImage, String code, int expireTime){
        this(bufferedImage, code, LocalDateTime.now().plusSeconds(expireTime));
    }

    /**
     * 验证码是否过期
     * @return
     */
    public boolean isExpire(){
        return LocalDateTime.now().isAfter(expireTime);
    }

}
