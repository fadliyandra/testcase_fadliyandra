package com.example.casefitnesscenter.security.filter;


import com.example.casefitnesscenter.security.SecurityUtils;
import com.example.casefitnesscenter.security.jwt.JwtService;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.casefitnesscenter.constant.ApiUrlConstant.AUTHENTICATION_URL;


@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals(AUTHENTICATION_URL + "/signin")
           || request.getServletPath().equals(AUTHENTICATION_URL + "/refreshToken")) {
            filterChain.doFilter(request, response);
            return;
        }
        log.info("Incoming request: {} {}", request.getMethod(), request.getRequestURI());
        String headerAuthorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            if (Objects.nonNull(headerAuthorization)) {
                String accessToken = jwtService.getJwtTokenFromHeader(request);
                JWTClaimsSet jwtClaimsSet = jwtService.parseToken(accessToken);
                String username = jwtClaimsSet.getSubject();

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                var roles = (List<Map<String, Object>>) jwtClaimsSet.getClaim("role");
                List<SimpleGrantedAuthority> authorities = roles == null ? null : roles.stream()
                        .map(args -> new  SimpleGrantedAuthority(args.get("role").toString()))
                        .toList();

                SecurityUtils.authenticateUserWithoutCredentials(request, userDetails, authorities);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
        filterChain.doFilter(request, response);
    }

}
