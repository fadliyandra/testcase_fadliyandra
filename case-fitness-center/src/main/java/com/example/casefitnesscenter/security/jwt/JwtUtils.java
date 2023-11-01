package com.example.casefitnesscenter.security.jwt;

import com.example.casefitnesscenter.common.exception.AppRuntimeException;
import com.example.casefitnesscenter.entity.dto.JwtResponse;
import com.example.casefitnesscenter.entity.model.AppUser;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

import static com.example.casefitnesscenter.constant.AppFitnessConstant.CANNOT_BE_BLANK;
import static com.example.casefitnesscenter.constant.SecurityConstant.*;


@RequiredArgsConstructor
@Component
@Slf4j
public class JwtUtils implements JwtService {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    public <T extends GrantedAuthority> JwtResponse generateJwtToken(AppUser appUser, Collection<T> authorities, String issuer) {
        String username = appUser.getUsername();
        Validate.notBlank(username, CANNOT_BE_BLANK);
        JWTClaimsSet claimsSetAccessToken = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer(issuer)
                .claim("authorities", authorities)
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME_MS))
                .build();
        String accessToken = generateAccessToken(claimsSetAccessToken);
        log.info(TOKEN_CREATED_SUCCESS, accessToken);

        JWTClaimsSet claimsSetRefreshToken = new JWTClaimsSet.Builder()
                .subject(username)
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME_MS))
                .build();
        String refreshToken = generateAccessToken(claimsSetRefreshToken);
        log.info(REFRESH_TOKEN_CREATED_SUCCESS, refreshToken);

        long durationExpiredAccessToken = claimsSetAccessToken.getExpirationTime().getTime() - claimsSetAccessToken.getIssueTime().getTime();
        long durationExpiredRefreshToken = claimsSetRefreshToken.getExpirationTime().getTime() - claimsSetRefreshToken.getIssueTime().getTime();

        return JwtResponse.builder()
                .userId(appUser.getMember().getId())
                .username(username)
                .accessToken(accessToken)
                .expirationAccessToken(durationExpiredAccessToken)
                .refreshToken(refreshToken)
                .expirationRefreshToken(durationExpiredRefreshToken)
                .type(TOKEN_PREFIX.trim())
                .build();
    }

    /**
     * Retrieves the jwt token from the request header if present and valid.
     *
     * @param request the httpRequest
     * @return the jwt token
     */
    @Override
    public String getJwtTokenFromHeader(HttpServletRequest request) {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isNotBlank(headerAuth)
            && headerAuth.startsWith(TOKEN_PREFIX)) {
            return headerAuth.substring(7); // 10 day of expired at token
        }
        return null;
    }

    @Override
    public JWTClaimsSet parseToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            byte[] secretKeyBytes = secretKey.getBytes();
            signedJWT.verify(new MACVerifier(secretKeyBytes));
            ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
            JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(
                    JWSAlgorithm.HS256,
                    new ImmutableSecret<>(secretKeyBytes));
            jwtProcessor.setJWSKeySelector(keySelector);
            jwtProcessor.process(signedJWT, null);

            return signedJWT.getJWTClaimsSet();
        } catch (ParseException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new AppRuntimeException(e.getMessage());
        } catch (BadJOSEException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new AppRuntimeException(e.getMessage());
        } catch (JOSEException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new AppRuntimeException(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new AppRuntimeException(e.getMessage());
        }
    }

    private String generateAccessToken(JWTClaimsSet jwtClaimsSet) {
        try {
            Payload payload = new Payload(jwtClaimsSet.toJSONObject());
            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), payload);
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppRuntimeException(e);
        }
    }
}
