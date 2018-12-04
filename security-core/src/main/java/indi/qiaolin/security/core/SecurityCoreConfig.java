package indi.qiaolin.security.core;

import indi.qiaolin.security.core.property.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author qiaolin
 * @version 2018/11/26
 **/

@Configuration
// 开启一个配置，否则配置类无法使用
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

}
