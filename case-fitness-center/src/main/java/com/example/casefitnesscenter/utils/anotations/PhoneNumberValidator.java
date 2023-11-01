package com.example.casefitnesscenter.utils.anotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    String value;
    String regexp;

    @Override
    public void initialize(PhoneNumber phoneNumber) {
        this.value = phoneNumber.value();
        this.regexp = phoneNumber.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.length() > 13) {
            return false;
        }

        var patternPhone = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        var matcher = patternPhone.matcher(value);
        return matcher.find();
    }
}
