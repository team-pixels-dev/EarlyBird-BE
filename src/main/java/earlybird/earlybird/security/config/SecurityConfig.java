package earlybird.earlybird.security.config;

import earlybird.earlybird.security.authentication.jwt.JWTAuthenticationFilter;
import earlybird.earlybird.security.authentication.jwt.reissue.JWTReissueAuthenticationFilter;
import earlybird.earlybird.security.authentication.jwt.reissue.JWTReissueAuthenticationProvider;
import earlybird.earlybird.security.authentication.oauth2.OAuth2AuthenticationFilter;
import earlybird.earlybird.security.authentication.oauth2.OAuth2AuthenticationProvider;
import earlybird.earlybird.security.authentication.oauth2.user.OAuth2UserDetails;
import earlybird.earlybird.security.token.jwt.JWTUtil;
import earlybird.earlybird.security.token.jwt.access.CreateJWTAccessTokenService;
import earlybird.earlybird.security.token.jwt.refresh.CreateJWTRefreshTokenService;
import earlybird.earlybird.security.token.jwt.refresh.JWTRefreshTokenRepository;
import earlybird.earlybird.security.token.jwt.refresh.JWTRefreshTokenToCookieService;
import earlybird.earlybird.security.token.jwt.refresh.SaveJWTRefreshTokenService;
import earlybird.earlybird.user.UserRepository;
import earlybird.earlybird.user.dto.UserAccountInfoDTO;
import earlybird.earlybird.user.service.JoinUserService;

import jakarta.servlet.http.Cookie;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTRefreshTokenRepository JWTRefreshTokenRepository;
    private final UserDetailsService userDetailsService;
    private final JoinUserService joinUserService;
    private final CreateJWTAccessTokenService createJWTAccessTokenService;
    private final CreateJWTRefreshTokenService createJWTRefreshTokenService;
    private final JWTRefreshTokenToCookieService JWTRefreshTokenToCookieService;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final SaveJWTRefreshTokenService saveJWTRefreshTokenService;
    private final OAuth2AuthenticationProvider oAuth2AuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager(http);
        http.authenticationManager(authenticationManager);

        http.authorizeHttpRequests(
                auth ->
                        //
                        // auth.requestMatchers("/api/v1/login/oauth2").permitAll()
                        //                                .anyRequest().authenticated());
                        auth.anyRequest().permitAll());

        OAuth2AuthenticationFilter oAuth2AuthenticationFilter = oAuth2AuthenticationFilter();
        oAuth2AuthenticationFilter.setAuthenticationManager(authenticationManager);
        http.addFilterAt(oAuth2AuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        JWTReissueAuthenticationFilter jwtReissueAuthenticationFilter =
                jwtReissueAuthenticationFilter();
        http.addFilterBefore(jwtReissueAuthenticationFilter, OAuth2AuthenticationFilter.class);

        JWTAuthenticationFilter jwtAuthenticationFilter =
                new JWTAuthenticationFilter(jwtUtil, userRepository);
        http.addFilterAfter(jwtAuthenticationFilter, OAuth2AuthenticationFilter.class);

        configLogout(http);

        http.sessionManagement(
                (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);

        configCors(http);

        return http.build();
    }

    private void configLogout(HttpSecurity http) throws Exception {
        http.logout(
                logout ->
                        logout.logoutRequestMatcher(
                                        new AntPathRequestMatcher("/api/v1/logout", "POST"))
                                .logoutSuccessHandler(
                                        ((request, response, authentication) -> {
                                            UserAccountInfoDTO userInfo =
                                                    ((OAuth2UserDetails)
                                                                    authentication.getPrincipal())
                                                            .getUserAccountInfoDTO();
                                            //
                                            // deleteOAuth2TokenService.deleteByUserAccountInfoDTO(
                                            //
                                            // userInfo);
                                        }))
                                .deleteCookies("JSESSIONID", "refresh")
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .addLogoutHandler(
                                        ((request, response, authentication) -> {
                                            Cookie[] cookies = request.getCookies();

                                            for (Cookie cookie : cookies) {
                                                if (cookie.getName().equals("refresh")) {
                                                    String refresh = cookie.getValue();
                                                    JWTRefreshTokenRepository.deleteByRefreshToken(
                                                            refresh);
                                                }
                                            }
                                        })));
    }

    private void configCors(HttpSecurity http) throws Exception {
        http.cors(
                corsCustomizer ->
                        corsCustomizer.configurationSource(
                                request -> {
                                    CorsConfiguration configuration = new CorsConfiguration();

                                    configuration.setAllowedOriginPatterns(
                                            Collections.singletonList("*"));
                                    configuration.setAllowedMethods(Collections.singletonList("*"));
                                    configuration.setAllowCredentials(true);
                                    configuration.setAllowedHeaders(Collections.singletonList("*"));
                                    configuration.setMaxAge(3600L);

                                    configuration.setExposedHeaders(
                                            Collections.singletonList("Set-Cookie"));
                                    configuration.setExposedHeaders(
                                            Collections.singletonList("Authorization"));

                                    return configuration;
                                }));
    }

    private JWTReissueAuthenticationFilter jwtReissueAuthenticationFilter() {
        JWTReissueAuthenticationFilter jwtReissueAuthenticationFilter =
                new JWTReissueAuthenticationFilter(
                        createJWTAccessTokenService,
                        createJWTRefreshTokenService,
                        JWTRefreshTokenRepository,
                        saveJWTRefreshTokenService,
                        JWTRefreshTokenToCookieService);
        ProviderManager jwtReissueAuthFilterProviderManager =
                new ProviderManager(new JWTReissueAuthenticationProvider(jwtUtil, userRepository));
        jwtReissueAuthenticationFilter.setAuthenticationManager(
                jwtReissueAuthFilterProviderManager);
        return jwtReissueAuthenticationFilter;
    }

    private OAuth2AuthenticationFilter oAuth2AuthenticationFilter() {
        return new OAuth2AuthenticationFilter(
                createJWTAccessTokenService,
                createJWTRefreshTokenService,
                JWTRefreshTokenToCookieService);
    }

    private AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                authenticationManagerBuilder(http);
        authenticationManagerBuilder.authenticationProvider(oAuth2AuthenticationProvider);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        return authenticationManager;
    }

    private AuthenticationManagerBuilder authenticationManagerBuilder(HttpSecurity http) {
        return http.getSharedObject(AuthenticationManagerBuilder.class);
    }
}
