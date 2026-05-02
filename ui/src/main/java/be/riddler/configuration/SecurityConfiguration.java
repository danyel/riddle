package be.riddler.configuration;

import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import com.vaadin.flow.spring.security.stateless.VaadinStatelessSecurityConfigurer;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
class SecurityConfiguration {
    @Value("${application.auth.secret}")
    private String authSecret;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.with(VaadinSecurityConfigurer.vaadin(),
                configurer -> configurer.loginView("/login"));

        http.with(new VaadinStatelessSecurityConfigurer<>(),
                cfg -> cfg.withSecretKey()
                        .secretKey(new SecretKeySpec(
                                Base64.getDecoder().decode(authSecret),
                                JwsAlgorithms.HS256)
                        ).and().issuer("be.riddler")
        );

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/line-awesome/**", "/themes/**", "/VAADIN/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll();
            auth.requestMatchers("/v1/**").permitAll();
            auth.requestMatchers("/", "/question/**", "/questions", "/icons", "/login").permitAll();
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
}

@Controller
class SpaForwardingController {
    @RequestMapping(value = "{path:[^.]*}")
    String forward() {
        return "forward:/";
    }
}