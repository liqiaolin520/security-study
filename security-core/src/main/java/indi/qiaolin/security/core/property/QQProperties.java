package indi.qiaolin.security.core.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @author qiaolin
 * @version 2018/12/2
 **/
@Getter
@Setter
public class QQProperties extends SocialProperties {

    private String providerId = "qq";
}
