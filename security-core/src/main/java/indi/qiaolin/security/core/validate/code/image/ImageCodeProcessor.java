package indi.qiaolin.security.core.validate.code.image;

import indi.qiaolin.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * @author qiaolin
 * @version 2018/11/28
 **/
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Override
    protected void send(ServletWebRequest request, ImageCode validateCode) throws Exception {
        ImageIO.write(validateCode.getBufferedImage(), "JPEG", request.getResponse().getOutputStream());
    }

}
