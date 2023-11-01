package com.example.casefitnesscenter.security.jwt;


import com.example.casefitnesscenter.entity.dto.JwtResponse;
import com.example.casefitnesscenter.entity.model.AppUser;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface JwtService {
    /**
     * Generate a JwtToken for the specified appUser.
     *
     * @param appUser  the appUser
     * @param issuer the issuer token
     * @return the token
     */
    <T extends GrantedAuthority> JwtResponse generateJwtToken(AppUser appUser, Collection<T> authorities, String issuer);

    /**
     * Validates the Jwt token passed to it.
     *
     * @param token the token
     * @return if valid or not
     */
    JWTClaimsSet parseToken(String token);
    /**
     * Retrieves the jwt token from the request cookie or request header if present and valid.
     *
     * @param request the httpRequest
     * @return the jwt token
     */
    String getJwtTokenFromHeader(HttpServletRequest request);
}
