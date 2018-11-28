package indi.qiaolin.security.core.validate.code;

import indi.qiaolin.security.core.validate.code.image.ImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 验证码接口
 * @author qiaolin
 * @version 2018/11/26
 **/


@RestController
public class ValidateController {

    public static final String PROCESSOR_SUFFIX = "CodeProcessor";
    
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

    /**
     *  验证码获取
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @GetMapping("/code/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        ValidateCodeProcessor validateCodeProcessor = validateCodeProcessorMap.get(type + PROCESSOR_SUFFIX);
        validateCodeProcessor.create(new ServletWebRequest(request, response));
    }




}
