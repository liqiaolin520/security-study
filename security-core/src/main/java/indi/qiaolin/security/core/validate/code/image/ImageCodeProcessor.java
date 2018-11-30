package indi.qiaolin.security.core.validate.code.image;

import indi.qiaolin.security.core.property.SecurityConstants;
import indi.qiaolin.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * @author qiaolin
 * @version 2018/11/28
 **/
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Override
    protected void send(ServletWebRequest request, ImageCode validateCode) throws Exception {
        ImageIO.write(validateCode.getBufferedImage(), SecurityConstants.DEFAULT_IMAGE_CODE_FORMAT, request.getResponse().getOutputStream());
    }

}
