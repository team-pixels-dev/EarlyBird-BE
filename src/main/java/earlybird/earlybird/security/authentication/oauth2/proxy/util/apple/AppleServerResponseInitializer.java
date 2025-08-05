package earlybird.earlybird.security.authentication.oauth2.proxy.util.apple;

import earlybird.earlybird.security.authentication.oauth2.proxy.response.OAuth2AppleServerResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppleServerResponseInitializer {

    private final AppleServerResponseValidator appleServerResponseValidator;

    public void init(OAuth2AppleServerResponse response) {
        Claims claims = appleServerResponseValidator.getClaims(response);
        response.setClaims(claims);
        response.setProviderId(claims.get("sub", String.class));
        response.setEmail(claims.get("email", String.class));
    }
}
