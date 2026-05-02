package be.riddler.configuration;

import be.riddler.configuration.filter.AuthorizationFilter;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import com.vaadin.flow.spring.security.stateless.VaadinStatelessSecurityConfigurer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;
import org.springframework.security.web.SecurityFilterChain;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;


/**
 * SecurityConfiguration
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@Configuration
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SecurityConfiguration {
    private final ObjectMapper objectMapper;
    @Value("${application.auth.secret}")
    private String authSecret;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Register your login view to the view access checker mechanism
        http.with(VaadinSecurityConfigurer.vaadin(),
                configurer -> configurer.loginView("/login"));

        // Enable stateless authentication
        http.with(new VaadinStatelessSecurityConfigurer<>(),
                cfg -> cfg.withSecretKey()
                        .secretKey(new SecretKeySpec(
                                Base64.getDecoder().decode(authSecret),
                                JwsAlgorithms.HS256)
                        ).and().issuer("be.riddler")
        );

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/line-awesome/**", "/themes/**", "/VAADIN/**").permitAll();
            auth.requestMatchers("/v1/**").permitAll();
            auth.requestMatchers("/", "/questions", "/icons", "/login").permitAll();
            auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();
        });


        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> new User(
                username,
                "{noop}password",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_PARTICIPANT"))
        );
    }

    @Bean
    AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter(objectMapper);
    }
}
