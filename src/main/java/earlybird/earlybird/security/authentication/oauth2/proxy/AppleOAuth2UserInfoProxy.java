package earlybird.earlybird.security.authentication.oauth2.proxy;

import earlybird.earlybird.error.exception.auth.apple.LoadAppleP8KeyException;
import earlybird.earlybird.security.authentication.oauth2.proxy.response.OAuth2AppleServerResponse;
import earlybird.earlybird.security.authentication.oauth2.proxy.response.OAuth2ServerResponse;
import earlybird.earlybird.security.authentication.oauth2.proxy.util.apple.AppleServerResponseInitializer;
import io.jsonwebtoken.Jwts;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.spring6.context.SpringContextUtils;
import reactor.core.publisher.Mono;

import javax.naming.AuthenticationException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.security.Security;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <h3>애플 로그인 과정</h3>
 * 1. 클라이언트에서 전송한 Authorization Code로 Client Secret JWT 토큰 생성
 * <br>
 * 2. 애플 서버의 Client Secret 검증 API 호출
 * <br>
 * 3. 애플 서버에서 id_token 값 받은 후 사용자 정보 파싱
 */
@Component
public class AppleOAuth2UserInfoProxy implements OAuth2UserInfoProxy {

    private final String filePath;
    private final AppleServerResponseInitializer appleServerResponseInitializer;

    public AppleOAuth2UserInfoProxy(
            @Value("${spring.auth.apple.p8-file}") String filePath,
            AppleServerResponseInitializer appleServerResponseInitializer
    ) {
        this.filePath = filePath;
        this.appleServerResponseInitializer = appleServerResponseInitializer;
    }

    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * @param accessToken 애플에서 클라이언트에게 제공하는 Authorization Code
     * @return
     */
    @Override
    public OAuth2ServerResponse getOAuth2UserInfo(String accessToken) {
        String clientSecret = createClientSecret(accessToken);
        OAuth2AppleServerResponse response = callAppleApi(accessToken, clientSecret);
        appleServerResponseInitializer.init(response);
        return response;
    }

    private OAuth2AppleServerResponse callAppleApi(String accessToken, String clientSecret) {
        return WebClient.create("https://appleid.apple.com")
                .post()
                .uri("/auth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("code", accessToken)
                        .with("client_id", "com.earlybirdteam2024.early-bird")
                        .with("client_secret", clientSecret))
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse ->
                                Mono.error(
                                        new AuthenticationException(
                                                String.format(
                                                        "%s Error from apple: %s",
                                                        clientResponse.statusCode(),
                                                        clientResponse.bodyToMono(
                                                                String.class)))))
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        clientResponse ->
                                Mono.error(
                                        new AuthenticationException(
                                                String.format(
                                                        "%s Error from apple: %s",
                                                        clientResponse.statusCode(),
                                                        clientResponse.bodyToMono(
                                                                String.class)))))

                .bodyToMono(OAuth2AppleServerResponse.class)
                .block();
    }

    private String createClientSecret(String appleAuthorizationCode) {
        Instant now = Instant.now();
        int expiredMs = 30 * 24 * 60 * 60; // 30 days
        // TODO: 민감정보 분리하기
        return Jwts.builder()
                .header()
                    .add("kid", "FH4V72Y38Q") // kid: 애플 개발자 계정과 연결된 Apple 개인 키로, 로그인하기 위해 생성된 10자 키 식별자
                    .and()
                .claim("iss", "8KH5U6PJ48") // iss: 개발자 계정과 연결된 10자리의 팀 ID
                .claim("iat", Date.from(now)) // iat: client secret을 생성한 시간을 UTC 기준 이포크 이후 초 단위로 나타냄
                .claim("exp", Date.from(now.plusSeconds(expiredMs))) // exp: client secret이 만료되는 시간 또는 그 이후를 식별함
                .claim("sub", "com.earlybirdteam2024.early-bird") // sub: client_id
                .claim("aud", "https://appleid.apple.com") // aud: client-secret을 검증하기 위한 서버
                .signWith(loadAppleLoginP8Key())
                .compact();
    }

    private PrivateKey loadAppleLoginP8Key() {
        ClassPathResource resource = new ClassPathResource(filePath);
        try (PEMParser pemParser = new PEMParser(new InputStreamReader(resource.getInputStream()))) {
            Object object = pemParser.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

            if (object instanceof PrivateKeyInfo) {
                return converter.getPrivateKey((PrivateKeyInfo) object);
            } else if (object instanceof PEMKeyPair) {
                return converter.getKeyPair((PEMKeyPair) object).getPrivate();
            } else {
                throw new IllegalArgumentException("지원하지 않는 키 형식입니다.");
            }
        } catch (Exception e) {
            throw new LoadAppleP8KeyException();
        }
    }
}
