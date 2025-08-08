package earlybird.earlybird.security.authentication.oauth2.proxy.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OAuth2GoogleServerResponse implements OAuth2ServerResponse {
    private String id;
    private String name;
    private String email;

    @Override
    public String getProviderName() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }
}
