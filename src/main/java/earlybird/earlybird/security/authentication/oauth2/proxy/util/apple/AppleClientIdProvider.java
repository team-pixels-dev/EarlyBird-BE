package earlybird.earlybird.security.authentication.oauth2.proxy.util.apple;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppleClientIdProvider {

    @Value("${spring.auth.apple.client-id}")
    private String clientId;


}
