package earlybird.earlybird.security.authentication.jwt.reissue;

import earlybird.earlybird.security.jwt.access.CreateAccessTokenService;
import earlybird.earlybird.security.jwt.refresh.CreateRefreshTokenService;
import earlybird.earlybird.security.jwt.refresh.RefreshTokenRepository;
import earlybird.earlybird.security.jwt.refresh.RefreshTokenToCookieService;
import earlybird.earlybird.security.jwt.refresh.SaveRefreshTokenService;
import earlybird.earlybird.user.dto.UserAccountInfoDTO;
import earlybird.earlybird.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class JWTReissueAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/v1/reissue", "POST");
    private final CreateRefreshTokenService createRefreshTokenService;
    private final CreateAccessTokenService createAccessTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final SaveRefreshTokenService saveRefreshTokenService;
    private final RefreshTokenToCookieService refreshTokenToCookieService;

    public JWTReissueAuthenticationFilter(
            CreateAccessTokenService createAccessTokenService,
            CreateRefreshTokenService createRefreshTokenService,
            RefreshTokenRepository refreshTokenRepository,
            SaveRefreshTokenService saveRefreshTokenService,
            RefreshTokenToCookieService refreshTokenToCookieService
    ) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.createAccessTokenService = createAccessTokenService;
        this.createRefreshTokenService = createRefreshTokenService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.saveRefreshTokenService = saveRefreshTokenService;
        this.refreshTokenToCookieService = refreshTokenToCookieService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        Optional<Cookie> optionalRefreshCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refresh"))
                .findFirst();

        if (optionalRefreshCookie.isEmpty()) {
            throw new AuthenticationServiceException("refresh 토큰이 존재하지 않습니다.");
        }

        String refreshToken = optionalRefreshCookie.get().getValue();
        JWTReissueAuthenticationToken authToken = new JWTReissueAuthenticationToken(refreshToken);

        return this.getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        final int accessTokenExpiredMs = 60 * 60 * 60; // 60 * 60 * 60 ms = 36분
        final int refreshTokenExpiredMs = 86400000; // 86400000 ms = 24 h

        UserAccountInfoDTO userInfo = (UserAccountInfoDTO) authResult.getPrincipal();
        String accountId = userInfo.getAccountId();
        String role = userInfo.getRole();

        String newAccessToken = createAccessTokenService.createAccessToken(userInfo, (long) accessTokenExpiredMs);
        String newRefreshToken = createRefreshTokenService.createRefreshToken(userInfo, (long) refreshTokenExpiredMs);

        String oldRefreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refresh"))
                .findFirst()
                .get()
                .getValue();

        refreshTokenRepository.deleteByRefreshToken(oldRefreshToken);

        response.setHeader("access", newAccessToken);
        response.addCookie(refreshTokenToCookieService.createCookie(newRefreshToken, refreshTokenExpiredMs));
    }
}
