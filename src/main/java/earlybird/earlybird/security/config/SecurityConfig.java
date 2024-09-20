package earlybird.earlybird.security.config;

import earlybird.earlybird.security.authentication.oauth2.OAuth2AuthenticationFilter;
import earlybird.earlybird.security.authentication.oauth2.OAuth2AuthenticationProvider;
import earlybird.earlybird.security.authentication.oauth2.user.OAuth2UserJoinService;
import earlybird.earlybird.security.jwt.refresh.RefreshRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RefreshRepository refreshRepository;
    private final UserDetailsService userDetailsService;
    private final OAuth2UserJoinService oAuth2UserJoinService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(new OAuth2AuthenticationProvider(userDetailsService, oAuth2UserJoinService));
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .authenticationManager(authenticationManager);


        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                );

        OAuth2AuthenticationFilter oAuth2AuthenticationFilter = new OAuth2AuthenticationFilter();
        oAuth2AuthenticationFilter.setAuthenticationManager(authenticationManager);

        http
                .addFilterAt(oAuth2AuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

//        http
//                .authenticationProvider(new OAuth2AuthenticationProvider(userDetailsService, oAuth2UserJoinService));


        http
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                        .logoutSuccessHandler(((request, response, authentication) -> {

                        }))
                        .deleteCookies("JSESSIONID", "refresh")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .addLogoutHandler(((request, response, authentication) -> {
                            Cookie[] cookies = request.getCookies();

                            for (Cookie cookie : cookies) {
                                if (cookie.getName().equals("refresh")) {
                                    String refresh = cookie.getValue();
                                    refreshRepository.deleteByRefresh(refresh);
                                }
                            }
                        }))
                );

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);



        return http.build();
    }
}
