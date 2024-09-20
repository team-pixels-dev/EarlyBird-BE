package earlybird.earlybird.security.authentication.oauth2;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class OAuth2AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/v1/login", "POST");

    public OAuth2AuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String oauth2ProviderName = request.getParameter("provider-name");
        String oauth2AccessToken = request.getParameter("oauth2-access");

        if (oauth2ProviderName == null || oauth2AccessToken == null) {
            throw new IllegalArgumentException("provider-name 또는 oauth2-access 값이 제공되지 않았습니다.");
        }

        OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(oauth2ProviderName, oauth2AccessToken);

        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // TODO: 로그인 성공시, JWT 토큰 발급 구현

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // TODO: 실패 로직 구현
        System.out.println("인증 실패!");
    }
}
