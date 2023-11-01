package com.example.casefitnesscenter.constant;

public class UserConstant {
    private UserConstant() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NOT_MATCH_EMAIL = "Your email is not match with your request.";
    public static final String EMAIL_ALREADY_TAKEN = "Email has already registered";


    public static final String SUCCESSFULLY_CREATE = "%s you're successfully to register. Next step, validate your email!";
    public static final String UPDATE_PROFILE = "Profile has been updated";
    public static final String EMAIL_OR_USERNAME_NOT_PROVIDED_MSG = "E-mail is not registered.";
    public static final String PASSWORD_MISMATCH = "Wrong oldPassword. Please try again";
    public static final String USER_NOT_FOUND_MSG = "User is not found";

}
