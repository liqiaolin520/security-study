package indi.qiaolin.security;

import indi.qiaolin.security.support.SimpleResponse;
import indi.qiaolin.security.core.property.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qiaolin
 * @version 2018/11/23
 **/


@RestController
public class BrowserSecurityController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**  请求缓存类 */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /** 重定向策咯类 */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;


    /**
     *  身份认证完毕后调用的逻辑
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("authentication/require")
    public Object loginSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 取出引起转发的请求，我猜spring security 转发之前会把原请求放入到新请求的session域内
        SavedRequest saveRequest = requestCache.getRequest(request, response);

        if(saveRequest != null){
            String redirectUrl = saveRequest.getRedirectUrl();
            logger.info("引起转发的URL是：{}", redirectUrl);
            if(StringUtils.endsWithIgnoreCase(redirectUrl, ".html")){
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }


        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登陆页面");
    }

}
