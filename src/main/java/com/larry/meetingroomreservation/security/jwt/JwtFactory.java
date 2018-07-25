package com.larry.meetingroomreservation.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.larry.meetingroomreservation.domain.entity.support.RoleName;
import com.larry.meetingroomreservation.security.token.PostAuthorizationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtFactory {

    @Autowired
    private ObjectMapper objectMapper;

    private final Logger log = LoggerFactory.getLogger(JwtFactory.class);

    //??
//    @SuppressFBWarnings(value = "SE_BAD_FIELD", justification = "It's okay here")
    private Clock clock = DefaultClock.INSTANCE;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUserIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public List<RoleName> getUserRoles(String token) {
        return getClaimFromToken(token, "authorities").stream().map(r -> RoleName.valueOf(r)).collect(Collectors.toList());
    }

    public List<String> getClaimFromToken(String token, String claimName) {
        final Claims claims = getAllClaimsFromToken(token);
        log.info("auth token : {}", claims.get(claimName, String.class));
        String authorities = claims.get(claimName, String.class);
        return Arrays.asList(authorities.substring(1, authorities.length() - 1).replaceAll("\\s", "").split(","));
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateToken(Authentication auth) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        log.info("roles : {}", auth.getAuthorities());
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(((PostAuthorizationToken)auth).getUserId())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .claim("authorities", auth.getAuthorities().toString())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean isValidateToken(String token) {
        final String userId = getUserIdFromToken(token);
        if (userId == null) {
            throw new RuntimeException("토큰의 계정 이름이 담겨있지 않습니다.");
        }
        if (isTokenExpired(token)) {
            throw new RuntimeException("토큰이 만료되었습니다.");
        }
        return true;
    }

    private Date calculateExpirationDate(Date createdDate) {
        log.info("date is null? : {}", createdDate);
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
