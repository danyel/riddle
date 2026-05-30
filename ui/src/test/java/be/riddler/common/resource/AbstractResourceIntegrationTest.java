package be.riddler.common.resource;

import be.riddler.configuration.TestcontainersConfiguration;
import be.riddler.configuration.filter.JwtAuthenticationFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * AbstractResourceTest
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@Import({TestcontainersConfiguration.class})
public abstract class AbstractResourceIntegrationTest {
    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected ObjectMapper objectMapper;

    @Value("${application.auth.secret}")
    private String authSecret;
    private final Map<String, String> cookies = new HashMap<>();

    @BeforeEach
    void setUp() {
        var secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(authSecret));
        var fullToken = Jwts.builder()
                .subject("test-user")
                .claim("roles", List.of("ROLE_USER"))
                .expiration(Date.from(Instant.now().plusSeconds(3600)))
                .signWith(secretKey)
                .compact();

        var lastPeriodIndex = fullToken.lastIndexOf('.');
        var headerAndPayload = fullToken.substring(0, lastPeriodIndex);
        var signature = fullToken.substring(lastPeriodIndex + 1);
        cookies.put(JwtAuthenticationFilter.JWT_HEADER_AND_PAYLOAD, headerAndPayload);
        cookies.put(JwtAuthenticationFilter.JWT_SIGNATURE, signature);
    }

    protected String jwtSignature() {
        return cookies.get(JwtAuthenticationFilter.JWT_SIGNATURE);
    }

    protected String jwtHeaderAndPayload() {
        return cookies.get(JwtAuthenticationFilter.JWT_HEADER_AND_PAYLOAD);
    }

    protected String xAuthenticationContent() {
        var authentication = new UsernamePasswordAuthenticationToken("username", "password", List.of((GrantedAuthority) () -> "AMDIN"));
        var authenticationBytes = objectMapper.writeValueAsBytes(authentication);
        return Base64.getEncoder().encodeToString(authenticationBytes);
    }
}
