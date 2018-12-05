package indi.qiaolin.security.core.social;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author qiaolin
 * @version 2018/12/4
 **/

public class MyConnectView extends AbstractView {
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        if(model.get("connections") != null){
            response.getWriter().print("<h2>绑定成功</h2>");
        }else{
            response.getWriter().print("<h2>解绑成功</h2>");
        }
    }

}
