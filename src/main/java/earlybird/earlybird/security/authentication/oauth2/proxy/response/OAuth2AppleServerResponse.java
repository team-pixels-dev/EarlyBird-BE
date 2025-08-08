package earlybird.earlybird.security.authentication.oauth2.proxy.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.jsonwebtoken.Claims;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class OAuth2AppleServerResponse implements OAuth2ServerResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @Getter
    @JsonProperty("id_token")
    private String idToken;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @Getter @Setter @JsonIgnore private Claims claims;

    @Getter @Setter @JsonIgnore private String providerId;

    @Getter @Setter @JsonIgnore private String email;

    @Override
    public String getProviderName() {
        return "apple";
    }

    @Override
    public String getName() {
        return "apple user";
    }
}
