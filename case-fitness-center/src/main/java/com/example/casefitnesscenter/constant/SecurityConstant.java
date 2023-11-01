package com.example.casefitnesscenter.constant;

public class SecurityConstant {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String MUST_BE_AUTHENTICATED = "You must be authenticated";
    public static final String BLANK_USERNAME = "Field is required can't be blank.";
    public static final String TOKEN_CREATED_SUCCESS = "Token created successfully as {}.";
    public static final String REFRESH_TOKEN_CREATED_SUCCESS = "Refresh token created successfully as {}.";
    public static final long ACCESS_TOKEN_EXPIRATION_TIME_MS = 3_600_000;
    public static final long REFRESH_TOKEN_EXPIRATION_TIME_MS = 86_400_000;
    private SecurityConstant() {
    }
}
