package be.riddler.configuration;

import be.riddler.configuration.filter.AuthorizationFilter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * SecurityConfiguration
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SecurityConfiguration {
    private final ObjectMapper objectMapper;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/v1/**").permitAll();
                    auth.requestMatchers("/swagger-ui/**",
                                            "/v3/api-docs/**",
                                            "/swagger-ui.html")
                                    .permitAll();
                            auth.requestMatchers("/**").permitAll();
                        }
                )
                .build();
    }

    @Bean
    UserDetailsManager userDetailsManager() {
        return new UserDetailsManager() {
            @Override
            public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
                return new User(username, "password", List.of((GrantedAuthority) () -> "ADMIN"));
            }

            @Override
            public void createUser(@NonNull UserDetails user) {

            }

            @Override
            public void updateUser(@NonNull UserDetails user) {

            }

            @Override
            public void deleteUser(@NonNull String username) {

            }

            @Override
            public void changePassword(@NonNull String oldPassword, @NonNull String newPassword) {

            }

            @Override
            public boolean userExists(@NonNull String username) {
                return true;
            }
        };
    }

    @Bean
    AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter(objectMapper);
    }
}
