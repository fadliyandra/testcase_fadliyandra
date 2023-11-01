package com.example.casefitnesscenter.constant;

import jakarta.validation.constraints.NotNull;
import org.slf4j.helpers.MessageFormatter;

import java.util.Arrays;

public final class AppFitnessConstant {
    public static final String SUCCESSFULLY_DELETE_OPERATION = "Successfully to deleted %s ";
    public static final String SUCCESSFULLY_ADD_NEW_ENTITY = "Successfully to add new %s ";
    public static final String NO_CONTENT = "No content found for %s ";
    public static final String MUST_BE_UNIQUE = "Name %s must be unique";
    public static final String UNIQUE_PHONE_NUMBER = "Phone number is already registered";
    public static final String NOT_FOUND_MSG = "Error: {} is not found";
    // AbstractCrudView
    public static final String CANNOT_BE_BLANK = "%s cannot be blank";
    public static final String NOT_VALID_FIELD = "%s is not valid";

    private AppFitnessConstant() {
        throw new IllegalStateException("Utility class");
    }

    public static String messageFormat(String message, String... s) {
        return MessageFormatter.format(message, s).getMessage();
    }

    public static String concatenate(@NotNull String delimiter, Object... s) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(s).forEach(args -> sb.append(args).append(delimiter));

        return sb.toString();
    }
}
