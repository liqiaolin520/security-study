package indi.qiaolin.security.web.controller;

import indi.qiaolin.security.core.property.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author qiaolin
 * @version 2018/12/3
 **/


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    //@Autowired
    //private AppSignUpUtils appSignUpUtils;

    @Autowired
    private SecurityProperties securityProperties;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @PostMapping("/register")
    public void register(String username, String password, HttpServletRequest request, HttpServletResponse response) throws IOException {
       // providerSignInUtils.doPostSignUp(username, new ServletWebRequest(request));
        //redirectStrategy.sendRedirect(request, response,  "/index.html");
       // appSignUpUtils.doPostSignUp(new ServletWebRequest(request), username);
    }

    @GetMapping("me")
    public Authentication me(HttpServletRequest request) throws UnsupportedEncodingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String token = StringUtils.substringAfter(request.getHeader("Authorization"), "bearer ");

        Claims body = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
                .parseClaimsJws(token).getBody();

        Object company = body.get("company");

        log.info("company name --> {} ", company);

        return authentication;

    }

    @GetMapping("/me/annotation")
    public UserDetails me(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }

}
