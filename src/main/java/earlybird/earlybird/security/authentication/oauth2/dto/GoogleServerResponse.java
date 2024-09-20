package earlybird.earlybird.security.authentication.oauth2.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GoogleServerResponse implements OAuth2ServerResponse {
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
