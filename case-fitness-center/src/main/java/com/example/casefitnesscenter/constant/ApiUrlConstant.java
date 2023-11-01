package com.example.casefitnesscenter.constant;

public final class ApiUrlConstant {
    public static final String AUTHENTICATION_URL = "/api/v3/auth";
    public static final String SWAGGER_API_DOCS = "/v3/api-docs/**";
    public static final String SWAGGER_API = "/swagger-ui/**";
    public static final String HEALTH_PROGRAMS_URL = "/api/v1/health-programs";
    public static final String MEMBERSHIP_URL = "/api/v1/members";
    public static final String SUBSCRIPTION_URL = "/api/v1/subscriptions";
    public static final String PAYMENT_URL = "/api/v1/payments";
    private ApiUrlConstant() {
        throw new IllegalStateException("Utility class");
    }
}
