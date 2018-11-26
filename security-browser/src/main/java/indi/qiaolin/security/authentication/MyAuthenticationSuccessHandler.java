package indi.qiaolin.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.qiaolin.security.core.property.LoginType;
import indi.qiaolin.security.core.property.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 认证成功处理器
 * 需要实现  AuthenticationSuccessHandler接口，
 * 我们这里使用他的子类， SavedRequestAwareAuthenticationSuccessHandler
 * 这个子类的  onAuthenticationSuccess 方法实现了跳转到登陆之前访问的页面
 * @author qiaolin
 * @version 2018/11/26
 **/

@Slf4j
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     *  认证成功时调用
     * @param request
     * @param response
     * @param authentication 认证对象，里面包含了用户的认证信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("登陆成功！");

        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(objectMapper.writeValueAsString(authentication));
        }else{
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }


}
