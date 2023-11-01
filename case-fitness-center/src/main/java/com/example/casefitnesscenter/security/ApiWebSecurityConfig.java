package com.example.casefitnesscenter.security;

import com.example.casefitnesscenter.security.filter.JwtAuthorizationFilter;
import com.example.casefitnesscenter.security.handler.AuthorizationFilterDenied;
import com.example.casefitnesscenter.security.handler.CustomAuthEntryPoint;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.example.casefitnesscenter.constant.ApiUrlConstant.*;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class ApiWebSecurityConfig {
    private final CustomAuthEntryPoint customAuthEntryPoint;
    private final AuthorizationFilterDenied accessDenied;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final LogoutHandler logoutHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;

    @Bean
    @Order(1)
    SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers(
                                new AntPathRequestMatcher("/error"),
                                new AntPathRequestMatcher(AUTHENTICATION_URL + "/**"),
                                new AntPathRequestMatcher(SWAGGER_API),
                                new AntPathRequestMatcher(SWAGGER_API_DOCS))
                        .permitAll()
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandler -> exceptionHandler
                        .accessDeniedHandler(accessDenied)
                        .authenticationEntryPoint(customAuthEntryPoint))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(logout -> logout
                        .logoutUrl(AUTHENTICATION_URL + "/sign-out")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(logoutSuccessHandler));

        return http.build();
    }
}
