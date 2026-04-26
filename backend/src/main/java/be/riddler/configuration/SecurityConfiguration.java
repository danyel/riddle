package be.riddler.configuration;

import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfiguration
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@EnableWebSecurity
@Configuration
class SecurityConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests(auth -> {
            // 1. Specific public API and UI paths
            auth.requestMatchers("/v1/**", "/participant/**", "/swagger-ui/**").permitAll();

            // 2. Explicitly secure the /secured path
            auth.requestMatchers("/secured/**").authenticated();

            // 3. Permitting all others here is usually handled by VaadinSecurityConfigurer
            // but if you want everything else public:
            auth.requestMatchers("/**").permitAll();
        });
        return http.with(VaadinSecurityConfigurer.vaadin(), configurer -> {
            // 1. All paths are secured by default with VaadinSecurityConfigurer
            // 2. Set the login view to /login
            configurer.loginView("/login");
        }).build();
    }

    @Bean
    UserDetailsManager userDetailsManager() {
        LoggerFactory.getLogger(SecurityConfiguration.class)
                .warn("NOT FOR PRODUCTION: Using in-memory user details manager!");
        var user = User.withUsername("dnoulet")
                .password("{noop}developer")
                .roles("USER")
                .build();
        var admin = User.withUsername("admin")
                .password("{noop}admin")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
