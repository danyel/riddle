package be.riddler.configuration.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * JwtAuthenticationFilter
 *
 * @author dnoulet
 * @version 1.0.0 03/05/2026
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final SecretKey secretKey;

    public JwtAuthenticationFilter(String base64Secret) {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Secret));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws IOException, ServletException {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            // 1. Extract the split token parts from the browser cookies
            String headerAndPayload = Arrays.stream(cookies)
                    .filter(c -> "jwt.headerAndPayload".equals(c.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);

            String signature = Arrays.stream(cookies)
                    .filter(c -> "jwt.signature".equals(c.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);

            // 2. Reconstruct the full JWS string token
            if (headerAndPayload != null && signature != null) {
                String fullToken = headerAndPayload + "." + signature;

                try {
                    Claims claims = Jwts.parser()
                            .verifyWith(secretKey)
                            .build()
                            .parseSignedClaims(fullToken)
                            .getPayload();

                    String username = claims.getSubject();

                    // Extract roles array from the cookie token claim directly
                    @SuppressWarnings("unchecked")
                    List<String> roles = claims.get("roles", List.class);
                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                            .map(SimpleGrantedAuthority::new)
                            .toList();

                    var authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } catch (Exception e) {
                    SecurityContextHolder.clearContext(); // Token tampered with or expired
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
