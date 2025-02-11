package com.vietpq.JobHunter.util.security;


import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

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
                .claim("permission", authentication)
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder().encode(JwtEncoderParameters.from(jwsHeader,claims)).getTokenValue();
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(getSecretKey()).macAlgorithm(JWT_ALGORITHM).build();
        return token->{
            try {
                return jwtDecoder.decode(token);
            } catch (Exception e) {
                System.out.println(">>> JWT error: " + e.getMessage());
                throw e;
            }
        };
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("permission");
        JwtAuthenticationConverter jwtAuthenticationConverter = new
                JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }


    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject();
        } else if (authentication.getPrincipal() instanceof String s) {
            return s;
        }
        return null;
    }

    public static Optional<String> getCurrentUserLogin(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }


}
