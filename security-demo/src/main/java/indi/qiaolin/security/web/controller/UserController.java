package indi.qiaolin.security.web.controller;

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

/**
 * @author qiaolin
 * @version 2018/12/3
 **/


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    //@Autowired
    //private AppSignUpUtils appSignUpUtils;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @PostMapping("/register")
    public void register(String username, String password, HttpServletRequest request, HttpServletResponse response) throws IOException {
       // providerSignInUtils.doPostSignUp(username, new ServletWebRequest(request));
        //redirectStrategy.sendRedirect(request, response,  "/index.html");
       // appSignUpUtils.doPostSignUp(new ServletWebRequest(request), username);
    }

    @GetMapping("me")
    public Authentication me(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/me/annotation")
    public UserDetails me(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }

}
