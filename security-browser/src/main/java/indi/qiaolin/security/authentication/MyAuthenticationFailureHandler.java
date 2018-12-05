package indi.qiaolin.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.qiaolin.security.core.property.LoginType;
import indi.qiaolin.security.core.property.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 *  认证失败处理器
 *  需要实现 AuthenticationFailureHandler 接口
 *  但是这里我们用他的子类
 *  SimpleUrlAuthenticationFailureHandler
 *
 * @author qiaolin
 * @version 2018/11/26
 **/

@Slf4j
@Component
public class MyAuthenticationFailureHandler  extends SimpleUrlAuthenticationFailureHandler{

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {


        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){

            // 认证失败 401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            // 设置响应格式与编码
            response.setContentType("application/json;charset=UTF-8");

            // 写入响应信息
            response.getWriter().print(objectMapper.writeValueAsString(exception.getMessage()));
            exception.printStackTrace();
        }else{
            super.onAuthenticationFailure(request, response, exception);
        }
    }

}
