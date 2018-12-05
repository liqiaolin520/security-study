package indi.qiaolin.security.core.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查看当前账号已绑定的信息的试图
 * @see  org.springframework.social.connect.web.ConnectController#connectionStatus(org.springframework.web.context.request.NativeWebRequest, org.springframework.ui.Model)
 * 他会来寻找一个名字交  connect/status 的视图
 * @author qiaolin
 * @version 2018/12/4
 **/

@Component("connect/status")
public class MyConnectionStatusView extends AbstractView{

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, List<Connection<?>>> connectionMap = (Map<String, List<Connection<?>>>) model.get("connectionMap");
        Map<String, Boolean> map = new HashMap<>(connectionMap.size());
        connectionMap.forEach((k,v) ->{
            map.put(k, CollectionUtils.isNotEmpty(v));
        });
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(objectMapper.writeValueAsString(map));
    }

}
