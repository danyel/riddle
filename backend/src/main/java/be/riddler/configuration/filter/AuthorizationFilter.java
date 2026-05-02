package be.riddler.configuration.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

/**
 * AuthorizationFilter
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class AuthorizationFilter extends OncePerRequestFilter {
    public static final String X_AUTHORIZATION = "X-Authorization";
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader(X_AUTHORIZATION);

        if (authHeader != null && !authHeader.isBlank()) {
            try {
                byte[] decoded = Base64.getDecoder().decode(authHeader);
                // Ensure your ObjectMapper has the necessary modules/mixins for Spring Security classes
                var auth = objectMapper.readValue(decoded, UsernamePasswordAuthenticationToken.class);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                SecurityContextHolder.clearContext(); // Fail safe
            }
        }

        filterChain.doFilter(request, response);
    }
}
