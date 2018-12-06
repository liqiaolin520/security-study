package indi.qiaolin.security.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 资源服务器配置
 * @author qiaolin
 * @version 2018/12/6
 **/

@Configuration
@EnableResourceServer // 必须加上这个注解，否则无法使用令牌访问接口
public class ResourceServerConfig {
}
