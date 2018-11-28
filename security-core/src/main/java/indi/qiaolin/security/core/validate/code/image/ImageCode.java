package indi.qiaolin.security.core.validate.code.image;

import indi.qiaolin.security.core.validate.code.ValidateCode;
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
public class ImageCode extends ValidateCode{

    /** 图片缓冲对象 */
    private BufferedImage bufferedImage;

    /**
     * @param bufferedImage 图片对象
     * @param code 验证码
     * @param expireTime 从现在开始多少秒过期
     */
    public ImageCode(BufferedImage bufferedImage, String code, int expireTime){
        super(code, LocalDateTime.now().plusSeconds(expireTime));
        this.bufferedImage = bufferedImage;
    }

}
