package indi.qiaolin.security.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.qiaolin.security.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qiaolin
 * @version 2018/12/5
 **/

@Slf4j
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {

    /** 退出登陆成功跳转的地址 */
    private String signOutUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    public DefaultLogoutSuccessHandler(String signOutUrl){
        this.signOutUrl = signOutUrl;
    }


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("退出成功！");
        if(StringUtils.isBlank(signOutUrl)){
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(objectMapper.writeValueAsString(new SimpleResponse("退出成功！")));
        }else{
            response.sendRedirect(signOutUrl);
        }
    }

}
