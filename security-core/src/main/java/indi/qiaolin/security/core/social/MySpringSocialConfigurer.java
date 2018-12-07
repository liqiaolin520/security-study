package indi.qiaolin.security.core.social;

import lombok.Setter;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author qiaolin
 * @version 2018/12/2
 **/
@Setter
public class MySpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessUrl;

    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    public MySpringSocialConfigurer(String filterProcessUrl){
        this.filterProcessUrl = filterProcessUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessUrl);
        if(socialAuthenticationFilterPostProcessor != null){
            socialAuthenticationFilterPostProcessor.process(filter);
        }
        return (T)filter;

    }
}
