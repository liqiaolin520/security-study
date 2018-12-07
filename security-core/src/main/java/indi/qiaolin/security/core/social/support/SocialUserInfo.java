package indi.qiaolin.security.core.social.support;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qiaolin
 * @version 2018/12/3
 **/

@Getter
@Setter
public class SocialUserInfo {

    private String providerId;

    private String providerUserId;

    private String nickName;

    private String headImg;
}
