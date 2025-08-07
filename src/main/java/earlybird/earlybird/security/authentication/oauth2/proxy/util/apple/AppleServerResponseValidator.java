package earlybird.earlybird.security.authentication.oauth2.proxy.util.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import earlybird.earlybird.error.exception.auth.apple.VerifyAppleIdTokenException;
import earlybird.earlybird.security.authentication.oauth2.proxy.response.OAuth2AppleServerResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class AppleServerResponseValidator {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String issuer = "https://appleid.apple.com";
    private final String clientId;

    public AppleServerResponseValidator(@Value("${spring.auth.apple.client-id}") String clientId) {
        this.clientId = clientId;
    }


    public Claims getClaims(OAuth2AppleServerResponse response) {
        try {
            // 1. Identity Token의 Header에서 alg, kid 추출
            String idToken = response.getIdToken();
            String[] tokenParts = idToken.split("\\.");
            if (tokenParts.length != 3) {
                throw new IllegalArgumentException("Invalid JWT format");
            }

            String header = new String(Base64.getUrlDecoder().decode(tokenParts[0]));
            JsonNode headerNode = objectMapper.readTree(header);
            String alg = headerNode.get("alg").asText();
            String kid = headerNode.get("kid").asText();

            // 2. Apple 공개키 목록 가져오기
            JsonNode publicKeys = getApplePublicKeys();

            // 3. 매칭되는 공개키 찾기
            JsonNode matchedKey = findMatchedPublicKey(publicKeys, alg, kid);

            // 4. 공개키 인스턴스 생성
            PublicKey publicKey = generatePublicKey(matchedKey);

            // 5. JJWT를 사용하여 토큰 검증 및 파싱
            JwtParser parser = Jwts.parser()
                    .verifyWith(publicKey)
                    .requireIssuer(issuer)
                    .requireAudience(clientId)
                    .build();

            Jws<Claims> jws = parser.parseSignedClaims(idToken);
            Claims claims = jws.getPayload();

            // 6. 추가 검증 (필요시)
            validateClaims(claims);

            return claims;
        } catch (Exception e) {
            throw new VerifyAppleIdTokenException();
        }
    }

    private Map<String, String> getAlgAndKidFromIdToken(String idToken) throws ParseException, JsonProcessingException {

        String header = idToken.split("\\.")[0];
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String headerJson = new String(decoder.decode(header), StandardCharsets.UTF_8);

        JsonNode headerNode = objectMapper.readTree(headerJson);

        Map<String, String> algAndKid = new HashMap<>();
        algAndKid.put("alg", headerNode.get("alg").asText());
        algAndKid.put("kid", headerNode.get("kid").asText());

        return algAndKid;
    }

    private JsonNode getApplePublicKeys() {
        String jsonBody = WebClient.create("https://appleid.apple.com")
                .get()
                .uri("/auth/keys")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            return objectMapper.readTree(jsonBody).get("keys");
        } catch (JsonProcessingException e) {
            throw new VerifyAppleIdTokenException();
        }
    }

    private JsonNode findMatchedPublicKey(JsonNode publicKeys, String alg, String kid) {
        if (publicKeys == null || !publicKeys.isArray()) {
            throw new VerifyAppleIdTokenException();
        }

        for (JsonNode keyNode : publicKeys) {
            String keyAlg = keyNode.get("alg").asText();
            String keyKid = keyNode.get("kid").asText();

            if (alg.equals(keyAlg) && kid.equals(keyKid)) {
                return keyNode;
            }
        }

        throw new VerifyAppleIdTokenException();
    }

    private PublicKey generatePublicKey(JsonNode keyNode) {
        try {
            String kty = keyNode.get("kty").asText();
            if (!"RSA".equals(kty)) {
                throw new IllegalArgumentException("Unsupported key type: " + kty);
            }

            byte[] nBytes = Base64.getUrlDecoder().decode(keyNode.get("n").asText());
            byte[] eBytes = Base64.getUrlDecoder().decode(keyNode.get("e").asText());

            BigInteger modulus = new BigInteger(1, nBytes);
            BigInteger exponent = new BigInteger(1, eBytes);

            RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePublic(spec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Failed to generate RSA public key", e);
        }
    }

    private void validateClaims(Claims claims) {
        // 발급자 검증
        if (!issuer.equals(claims.getIssuer())) {
            throw new IllegalArgumentException("Invalid issuer: " + claims.getIssuer());
        }

        // 대상자 검증
        if (claims.getAudience().stream().noneMatch(audience -> audience.equals(clientId))) {
            throw new IllegalArgumentException("Invalid audience: " + claims.getAudience());
        }

        // 만료 시간은 JJWT가 자동으로 검증하므로 별도 처리 불필요
    }
}
