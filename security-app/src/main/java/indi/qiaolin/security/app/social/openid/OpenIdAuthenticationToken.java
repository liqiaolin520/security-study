package indi.qiaolin.security.app.social.openid;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author qiaolin
 * @version 2018/12/7
 **/

public class OpenIdAuthenticationToken extends AbstractAuthenticationToken{

    private final Object principal;

    @Setter
    @Getter
    private String providerId;

    public OpenIdAuthenticationToken(String principal, String providerId) {
        super(null);
        this.principal = principal;
        this.providerId = providerId;
        setAuthenticated(false);
    }

    public OpenIdAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities){
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

}
