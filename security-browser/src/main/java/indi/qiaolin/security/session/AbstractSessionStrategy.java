package indi.qiaolin.security.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.qiaolin.security.support.SimpleResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 抽象的session策略处理类
 * @author qiaolin
 * @version 2018/12/5
 **/

@Slf4j
public class AbstractSessionStrategy {

    /** 跳转的url */
    private String destinationUrl;

    /** 重定向策略 */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /** 跳转前是否创建新的session */
    @Setter
    private boolean createNewSession = true;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @param destinationUrl 要跳转的url
     */
    public AbstractSessionStrategy(String destinationUrl){
        Assert.isTrue(UrlUtils.isValidRedirectUrl(destinationUrl), "url must start with '/' or with 'http(s)'");
        this.destinationUrl = destinationUrl;
    }

    /**
     *  处理session因为过期，挤下线等问题
     * @param request
     * @param response
     */
    protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if(createNewSession){
            request.getSession();
        }

        String sourceUrl = request.getRequestURI();
        String targetUrl;
        if(StringUtils.endsWithIgnoreCase(sourceUrl, ".html")){
            targetUrl = destinationUrl + ".html";
            log.info("session失效，跳转到{}", targetUrl);
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }else{
            Object result = buildResponseContent(request);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(objectMapper.writeValueAsString(result));
        }

    }

    public Object buildResponseContent(HttpServletRequest request){
        String message = "session已经失效";
        if(isConcurrency()){
            message = message + "，有可能是并发登陆导致的~！";
        }
        return  new SimpleResponse(message);
    }


    /**
     * session失效是否和并发登陆导致，（被人挤下线了）
     * @return
     */
    protected boolean isConcurrency(){
        return false;
    }


}
