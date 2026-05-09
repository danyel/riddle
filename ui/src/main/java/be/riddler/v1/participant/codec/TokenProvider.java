package be.riddler.v1.participant.codec;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * TokenProvider
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Component
public class TokenProvider {
    @Value("${application.auth.secret}")
    private String base64Secret;
    private SecretKey verificationKey;

    public String generateToken(UUID id) {
        Date now = new Date();
        long oneHour = 3600000;
        Date validityExpiry = new Date(now.getTime() + oneHour);

        return Jwts.builder()
                .subject(id.toString())
                .claims(Map.of("roles", List.of("")))
                .issuedAt(now)
                .expiration(validityExpiry)
                .signWith(verificationKey)
                .compact();
    }

    @PostConstruct
    void postConstruct() {
        byte[] decodedKeyBytes = Base64.getDecoder().decode(base64Secret);
        this.verificationKey = Keys.hmacShaKeyFor(decodedKeyBytes);
    }
}
