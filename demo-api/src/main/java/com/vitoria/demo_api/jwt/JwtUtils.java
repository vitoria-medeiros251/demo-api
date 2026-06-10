package com.vitoria.demo_api.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;


@Slf4j
public class JwtUtils {

    public static final String JWT_BEARER = "Bearer ";
    public static final String  JWT_AUTHORIZATION = "Authorization";
    public static final String  SECRET_KEY = "123449GJS3954A8F9K2L7QWERTYUIOP";
    public static final long EXPIRE_DAY = 0;
    public static final long  EXPIRE_HOURS = 0;
    public static final long EXPIRE_MINUTES = 2;
    private static Calendar start;


    private JwtUtils() {}


    private static Key generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    }

    private static Date toExpireDate(Date date) {
        LocalDateTime dateTime  = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAY).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static JwToken createToken(String username, String role) {
      Date issuedAT = new Date();
      Date limit = toExpireDate(issuedAT);
      String token = Jwts.builder()
              .setHeaderParam("typ", "JWT")
              .setSubject(username)
              .issuedAt(issuedAT)
              .setExpiration(limit)
              .signWith(generateKey(), SignatureAlgorithm.HS256)
              .claim("role", role)
              .compact();
      return new JwToken(token);
    }

    private static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) generateKey())
                    .build()
                    .parseSignedClaims(refactorToken(token)).getPayload();
        } catch (JwtException ex) {
            log.error(String.format("Token invalido %s", ex.getMessage()));
        }
        return null;
    }

      private static String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
      }

    public static boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) generateKey())
                    .build()
                    .parseSignedClaims(refactorToken(token));
            return true;
        } catch (JwtException ex) {
            log.error(String.format("Token invalido %s", ex.getMessage()));
        }
        return false;
    }



    private static String refactorToken(String token) {
        if(token.contains(JWT_BEARER)){
            return token.substring(JWT_BEARER.length());
        }
        return token;
    }
}


