package bg.moviebox.service.impl;

import bg.moviebox.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    // signature = SHA256(key+text)
    // sign message = (text, signature) -> person who receives it and knows the key can execute this check ->
    // signature = SHA256(key+text)
    // the token is not crypt it's a plain text + digit signature

    private final String jwtSecret;
    private final long expiration;

    public JwtServiceImpl(@Value("${jwt.secret}") String jwtSecret,
                          @Value("${jwt.expiration}") long expiration) {
        this.jwtSecret = jwtSecret;
        this.expiration = expiration;
    }

    @Override
    public String generateToken(String userId, Map<String, Object> claims) {
        Date now = new Date();
//        Date expiration = new Date(now.getTime() + 3600000);  // Set expiration time to 1 hour from now

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
