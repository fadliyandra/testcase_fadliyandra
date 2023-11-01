package com.example.casefitnesscenter.utils;


import com.example.casefitnesscenter.entity.model.AppUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.util.Collection;
import java.util.Objects;

@Slf4j
public final class SecurityUtils {

    private SecurityUtils() {
        throw new AssertionError("This class cannot be instantiated");
    }

    public static AppUser getAuthenticatedUserDetails() {
        if (isAuthenticated()) {
            return (AppUser) getAuthentication().getPrincipal();
        }

        return null;
    }

    /**
     * For Jwt authentication object.
     * Creates an authentication object with the userDetails then set authentication to
     * SecurityContextHolder.
     *
     * @param request     the {@link HttpServletRequest}
     * @param userDetails the from authentication
     * @param authorities the from authentication
     */
    public static <T extends GrantedAuthority> void authenticateUserWithoutCredentials(HttpServletRequest request, UserDetails userDetails, Collection<T> authorities) {
        if (Objects.nonNull(request) && Objects.nonNull(userDetails)) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    /**
     * Clears the securityContextHolder.
     */
    public static void clearAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    /**
     * Validates that the user is neither disabled, locked nor expired.
     *
     * @param userDetails the user details
     */
    public static void validateUserDetailsStatus(UserDetails userDetails) {
        log.debug("User details {}", userDetails);

        if (!userDetails.isEnabled()) {
            throw new DisabledException("User is disabled");
        }
        if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("User is locked");
        }
        if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("User is account is is non active");
        }
        if (!userDetails.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("User credentials expired");
        }
    }

    /**
     * Retrieve the authentication object from the current session.
     *
     * @return authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Returns true if the user is authenticated.
     *
     * @return if user is authenticated
     */
    private static boolean isAuthenticated() {
        return isAuthenticated(getAuthentication());
    }

    /**
     * Returns true if the user is authenticated.
     *
     * @param authentication the authentication object
     * @return if user is authenticated
     */
    private static boolean isAuthenticated(Authentication authentication) {
        return Objects.nonNull(authentication)
               && authentication.isAuthenticated()
               && !(authentication instanceof AnonymousAuthenticationToken);
    }
}