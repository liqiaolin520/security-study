package indi.qiaolin.security.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author qiaolin
 * @version 2018/12/5
 **/

public class DefaultExpiredSessionStrategy extends  AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    /**
     * @param destinationUrl 要跳转的url
     */
    public DefaultExpiredSessionStrategy(String destinationUrl) {
        super(destinationUrl);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }


    @Override
    protected boolean isConcurrency() {
        return true;
    }
}
