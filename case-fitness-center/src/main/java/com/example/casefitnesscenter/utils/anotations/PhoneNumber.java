package com.example.casefitnesscenter.utils.anotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {PhoneNumberValidator.class})
@Target({FIELD, ANNOTATION_TYPE, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface PhoneNumber {
    String value() default "";

    String regexp() default "(\\+62 ((\\d{3}([ -]\\d{3,})([- ]\\d{4,})?)|(\\d+)))|(\\(\\d+\\) \\d+)|\\d{3}( \\d+)+|(\\d+[ -]\\d+)|\\d+";

    String message() default "Phone number is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
