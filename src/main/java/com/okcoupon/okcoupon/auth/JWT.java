package com.okcoupon.okcoupon.auth;

import com.okcoupon.okcoupon.exceptions.JWTexpiredException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//todo: where to set the "Bearer?"
@Service
public class JWT {

    /**
     * The type of encryption we use for our Secret-Key
     */
    private final String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    /**
     * The private-key, a string that is part of the whole Secret-Key
     */
    private final String encodedSecretKey = "this+is+ok+coupon+key+and+it+is+the+best+team+ever+bye";
    /**
     * Creation of our Secret-Key, based on three definitions:
     * Algorithm, type of encryption
     * Encoded Form, our private-key
     * A Form, name of the format of our encoded-key
     */
    private final Key decodedSecretKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey),
            this.signatureAlgorithm);

    /**
     * Private method that creates the token, based on five definitions:
     * Claims, set the unique data of User-Details such password, id and role
     * Subject, set the first item that will be checked, the User-Name (email)
     * IssuedAt, set the start time when the token ia valid to use, which is NOW
     * Expiration, set the expiration time of the token, when it will be no longer valid to use (expired in 30 minutes)
     * SignWith, set the Secret-Key
     * @param claims unique data of User-Details such password, id and role
     * @param email the subject, the first item that will be checked
     * @return Maximum 256-byte unique JWT Token (String type)
     */
    private String createToken(Map<String, Object> claims, String email) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .signWith(decodedSecretKey)
                .compact();
    }

    /**
     * public method that in-use to creates the Claims Subject to forward creates the Token
     * @param userDetails unique details for each Client, holds the User-id, User-Password, User-Role
     *                    and the User-Name which is the email and the subject
     * @return Maximum 256-byte unique JWT Token (String type)
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDetails.getId());
        claims.put("userPass", userDetails.getUserPass());
        claims.put("userRole", userDetails.getClientType());
        return createToken(claims, userDetails.getUserName());
    }

    /**
     * Method that invoked when need to validate the token
     * called to validate the User-Details part of the token which are the Claims
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @return Claims that holds the unique User-Details for each Client
     * @throws ExpiredJwtException thrown when the Token has expired
     */
    private Claims extractAllClaims(String token) throws ExpiredJwtException {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(this.decodedSecretKey)
                .build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    /**
     * Method that indicates if the Token has expired
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @return True if the token has expired
     * False if the token still validity
     */
    private boolean isTokenExpired(String token) {
        try {
            extractAllClaims(token);
            return false;
        } catch (ExpiredJwtException err) {
            return true;
        }
    }

    /**
     * Method that invoked when need to validate the token
     * called to validate the Subject which is the User-Name part of the token
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @return String that holds the unique User-Name for each Client
     */
    private String extractUserEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Method that invoked when need to validate the token
     * called to validate the expiration-time of the token
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @return Date that indicates if the token-time has passed
     */
    private Date extractExpirationDate(String token) {
        return extractAllClaims(token).getExpiration();
    }

    /**
     * Method that invoked when need to validate the token,
     * using the extractClaims-method and expiredToken-method
     * @param token Maximum 256-byte unique JWT Token (String type)
     * @return object, an instance of UserDetails.Class initialized by the claims subject from the Token
     * @throws JWTexpiredException thrown when the Token has expired
     */
    public UserDetails validateToken(String token) throws JWTexpiredException {
        if (isTokenExpired(token)) {
            throw new JWTexpiredException();
        }

        return UserDetails.builder()
                .userName(extractUserEmail(token))
                .id(extractAllClaims(token).get("userId", Integer.class))
                .clientType(extractAllClaims(token).get("userRole", String.class))
                .build();
    }
}
