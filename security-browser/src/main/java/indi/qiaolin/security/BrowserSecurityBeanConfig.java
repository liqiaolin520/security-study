package indi.qiaolin.security;

import indi.qiaolin.security.core.property.SecurityProperties;
import indi.qiaolin.security.session.DefaultExpiredSessionStrategy;
import indi.qiaolin.security.session.DefaultInvalidSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 浏览器Bean配置
 * @author qiaolin
 * @version 2018/12/5
 **/

@Configuration
public class BrowserSecurityBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;


    @Bean
    @ConditionalOnMissingBean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new DefaultExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    @Bean
    @ConditionalOnMissingBean
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new DefaultInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

}
