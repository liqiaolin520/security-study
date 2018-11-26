package indi.qiaolin.security.core.property;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * 安全配置类
 * @author qiaolin
 * @version 2018/11/26
 **/


@Getter
@ConfigurationProperties(prefix = "qiao.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

}
