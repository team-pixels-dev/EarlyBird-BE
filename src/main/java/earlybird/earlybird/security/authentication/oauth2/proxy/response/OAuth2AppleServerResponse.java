package earlybird.earlybird.security.authentication.oauth2.proxy.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import earlybird.earlybird.error.exception.auth.apple.VerifyAppleIdTokenException;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.math.BigInteger;

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

    @Getter
    @Setter
    private Claims claims;

    @Getter
    @Setter
    private String providerId;

    @Getter
    @Setter
    private String email;

    @Override
    public String getProviderName() {
        return "apple";
    }

    @Override
    public String getName() {
        return "apple user";
    }
}
