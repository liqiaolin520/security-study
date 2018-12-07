package indi.qiaolin.security.app.social;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.stereotype.Component;

/**
 *
 * Spring SocialConfigurer的后置处理器，因为app不能和浏览器一样跳转到指定页面，所以我们需要让
 * 第三方登陆需要注册的时候跳转到接口保存当前社交的Connetion信息
 * @author qiaolin
 * @version 2018/12/7
 **/

@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(!"springSocialConfigurer".equals(beanName)){
            return bean;
        }
        SpringSocialConfigurer configurer = (SpringSocialConfigurer) bean;
        configurer.signupUrl("/social/signUp");
        return configurer;
    }
}
