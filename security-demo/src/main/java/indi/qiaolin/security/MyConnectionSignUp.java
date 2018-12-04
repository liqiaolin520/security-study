package indi.qiaolin.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * @author qiaolin
 * @version 2018/12/3
 **/

@Component
public class MyConnectionSignUp implements ConnectionSignUp{

    /**
     * 自定义通过第三封登陆进来但是是第一次进来的用户，spring social会找不到用户，
     * 但是 social 预留了这个接口，可以让用户给第一次进来的用户做一些自定义操作，比如我们偷偷的在数据库中新增一个用户，然后返回这个用户
     * 的Id，这个Id也会被social写到他的关系表中,
     * 这样的好处就是不用调到注册页面了，就感觉QQ或者微信像是万能通行证一样，方便，毕竟一般的人都讨厌注册
     * @param connection
     * @return
     */
    @Override
    public String execute(Connection<?> connection) {

       // 这里加入我们在数据库中增加了用户，并且返回的是这个用户的Id
        return connection.getKey().getProviderUserId();
    }
}
