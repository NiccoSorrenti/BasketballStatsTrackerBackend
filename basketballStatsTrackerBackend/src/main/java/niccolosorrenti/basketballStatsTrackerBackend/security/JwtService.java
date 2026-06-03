package niccolosorrenti.basketballStatsTrackerBackend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    // 🔐 chiave segreta (per ora hardcoded, poi la mettiamo in application.properties)
    private static final String SECRET_KEY = "super-secret-key-super-secret-key-super-secret-key-123";
    // ⏳ 1 giorno di validità
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // 🔵 GENERA TOKEN
    public String generateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // 🔍 ESTRAI USERNAME
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // ✅ VALIDAZIONE TOKEN
    public boolean isTokenValid(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return expiration.before(new Date());
    }
}