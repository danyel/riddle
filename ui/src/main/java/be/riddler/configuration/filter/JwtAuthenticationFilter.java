package be.riddler.configuration.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * JwtAuthenticationFilter
 *
 * @author dnoulet
 * @version 1.0.0 03/05/2026
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String JWT_HEADER_AND_PAYLOAD = "jwt.headerAndPayload";
    public static final String JWT_SIGNATURE = "jwt.signature";
    private final SecretKey secretKey;

    public JwtAuthenticationFilter(String base64Secret) {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Secret));
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws IOException, ServletException {
        var token = token(request);
        buildSecurityContext(token);
        filterChain.doFilter(request, response);
    }

    private static String token(@NonNull HttpServletRequest request) {
        var cookies = getCookies(request);
        if (cookies != null && cookies.size() == 2) {
            return cookies.get(JWT_HEADER_AND_PAYLOAD) + "." + cookies.get(JWT_SIGNATURE);
        } else if (request.getHeader("X-Authorization") != null) {
            return request.getHeader("X-Authorization");
        }
        return null;
    }

    private void buildSecurityContext(String token) {
        if (token == null) return;
        try {
            var claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            var username = claims.getSubject();

            @SuppressWarnings("unchecked")
            var roles = (List<String>) claims.get("roles", List.class);
            var authorities = mapAuthorities(roles);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, token, authorities));
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
    }

    private static @Nullable Map<@NonNull String, @NonNull String> getCookies(@NonNull HttpServletRequest request) {
        var cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> List.of(JWT_SIGNATURE, JWT_HEADER_AND_PAYLOAD).contains(cookie.getName()))
                    .collect(Collectors.toMap(Cookie::getName, Cookie::getValue));
        }
        return null;
    }

    private static @NonNull List<SimpleGrantedAuthority> mapAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
