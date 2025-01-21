package com.vietpq.JobHunter.util.security;


import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class SecurityUtil {

    @Value("${jwt.base64-secret}")
    private String jwtKey;

    @Value("${jwt.access-token-validity-in-seconds}")
    private long accessTokenExpiration;

    public static  final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    private SecretKey getSecretKey(){
        byte[]  keyBytes = Base64.from(jwtKey).decode();
        return  new SecretKeySpec(keyBytes,0, keyBytes.length, JWT_ALGORITHM.getName());
    }
    @Bean
    private JwtEncoder jwtEncoder (){
        return  new NimbusJwtEncoder(new ImmutableSecret<>(getSecretKey()));
    }

    public String createAccessToken(Authentication authentication){
        Instant now = Instant.now();
        Instant validity  = now.plus(accessTokenExpiration, ChronoUnit.SECONDS);

        JwtClaimsSet  claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(authentication.getName())
                .claim("vietpq", authentication)
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder().encode(JwtEncoderParameters.from(jwsHeader,claims)).getTokenValue();

    }

}
