package indi.qiaolin.security.core.authentication.mobile;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 手机登陆认证提供者
 * @author qiaolin
 * @version 2018/11/28
 **/

public class SmsCodeAuthenticationProvider implements AuthenticationProvider{

    @Setter
    private UserDetailsService userDetailsService;


    /**
     * 认证方法，这里其实是交给UserDetailsService去做
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken SmsCodeToken = (SmsCodeAuthenticationToken) authentication;

        UserDetails userDetails = userDetailsService.loadUserByUsername((String) SmsCodeToken.getPrincipal());

        if(userDetails == null){
            throw new InternalAuthenticationServiceException("无法获取用户信息！");
        }

        SmsCodeAuthenticationToken authenticationToken = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(SmsCodeToken.getDetails());

        return authenticationToken;
    }

    /**
     *  当前提供者是否支持ToKen类的验证方式
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
