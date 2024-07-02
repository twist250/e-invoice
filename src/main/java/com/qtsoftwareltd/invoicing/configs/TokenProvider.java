package com.qtsoftwareltd.invoicing.configs;

import com.qtsoftwareltd.invoicing.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class TokenProvider {
    private final ApplicationConfigs configs;

    private Claims getAllClaimsFromToken(String token) {
        Assert.notNull(token, "Token cannot be null");

        return Jwts.parserBuilder()
                .setSigningKey(configs.getJwtSecret().getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(User user) {
        return doGenerateToken(user);
    }

    private String doGenerateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("scope", user.getRole().name());
        claims.put("userId", user.getId());
        claims.put("names", user.getName());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("https://qtsoftwareltd.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + configs.getJwtExpirationInMs()))
                .signWith(Keys.hmacShaKeyFor(configs.getJwtSecret().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public boolean validateToken(String token, UserDetails userDetails, HttpServletRequest request) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

}
