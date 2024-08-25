package com.hbpaymentprocessing.hbpaymentprocessing.utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {

    @Autowired
    private Utility utility;

    private KeyPair keyPair;

    private synchronized void generateRSAKeyPair() throws NoSuchAlgorithmException {
        if (this.keyPair == null) {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA".trim());
            keyPairGenerator.initialize(2048);
            this.keyPair = keyPairGenerator.generateKeyPair();
        }

    }

    private PublicKey getPublicKey() throws NoSuchAlgorithmException {
        if (this.keyPair == null) {
            this.generateRSAKeyPair();
        }
        return this.keyPair.getPublic();
    }

    public String getPublicKeyAsString() throws NoSuchAlgorithmException {
        byte[] publicKeyBytes = this.getPublicKey().getEncoded();
        return Base64.getEncoder().encodeToString(publicKeyBytes);
    }

    private PrivateKey getPrivateKey() throws NoSuchAlgorithmException {
        if (this.keyPair == null) {
            this.generateRSAKeyPair();
        }
        return this.keyPair.getPrivate();
    }

    public String createJWTString(String email, Map<String, Object> claims) throws NoSuchAlgorithmException {
        Date currentDateTime = Date.from(utility.getCurrentUTCDateTime());
        Date expirationDateTime = Date.from(
                Instant.ofEpochMilli(currentDateTime.getTime()).plus(Duration.ofMinutes(30)));

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(email.trim())
                .setIssuedAt(currentDateTime)
                .setExpiration(expirationDateTime)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.RS256, this.getPrivateKey());
        return jwtBuilder.compact();
    }


    private Claims getClaimsFromJWTString(String jwtString) throws NoSuchAlgorithmException {
        return Jwts.parser()
                .setSigningKey(this.getPublicKey())
                .parseClaimsJws(jwtString.trim())
                .getBody();
    }

    private Date getJWTExpirationTime(String jwtString) throws NoSuchAlgorithmException {
        Claims claims = this.getClaimsFromJWTString(jwtString.trim());
        return claims.getExpiration();
    }

    public boolean checkJWTExpirationTime(String jwtString) throws NoSuchAlgorithmException {
        Date expirationTime = this.getJWTExpirationTime(jwtString.trim());
        return expirationTime.after(Date.from(utility.getCurrentUTCDateTime()));
    }

    private boolean checkRequestHeader(String reqHeader) {
        boolean isValid = false;
        if (reqHeader != null) {
            if (!reqHeader.isEmpty() & reqHeader.startsWith("Bearer ")) {
                isValid = true;
            }
        }
        return isValid;
    }

    public String getJWTStringFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization".trim());
        return this.checkRequestHeader(authHeader)
                ? authHeader.substring("Bearer ".length())
                : null;
    }

    public String getEmailFromJWTString(String jwtString) throws NoSuchAlgorithmException {
        Claims claims = this.getClaimsFromJWTString(jwtString.trim());
        return claims.getSubject().trim();
    }

    public String getRoleID(String jwtString) throws NoSuchAlgorithmException {
        Claims claims = this.getClaimsFromJWTString(jwtString.trim());
        return claims.get("roleId").toString();
    }

}
