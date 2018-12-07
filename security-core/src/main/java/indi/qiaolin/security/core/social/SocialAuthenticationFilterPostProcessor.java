package indi.qiaolin.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * 社交登陆过滤器后置处理器
 * @author qiaolin
 * @version 2018/12/7
 **/
public interface SocialAuthenticationFilterPostProcessor {

    /**
     *  可定制化第三方授权过滤器
     * @param filter
     */
    void process(SocialAuthenticationFilter filter);
}
