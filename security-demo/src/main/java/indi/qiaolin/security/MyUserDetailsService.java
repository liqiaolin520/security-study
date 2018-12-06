package indi.qiaolin.security ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 *
 * 用户明细服务类，spring security会自己来调用
 * @author qiaolin
 * @version 2018/11/20
 **/

@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {
    private Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);


    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     *  用户登陆是会来请求，传入得是用户得登陆名，需要返回用户信息，正式项目中应从数据库查询出来
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("登陆名：{}", username);


        String newPassword = passwordEncoder.encode("123456");

        logger.debug("密码加密后：{}", newPassword);

        return new User(username, newPassword, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user,ROLE_USER"));
    }


    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.debug("社交登陆名：{}", userId);


        String newPassword = passwordEncoder.encode("123456");

        logger.debug("密码加密后：{}", newPassword);

        return new SocialUser(userId, newPassword, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user,ROLE_USER"));

    }
}
