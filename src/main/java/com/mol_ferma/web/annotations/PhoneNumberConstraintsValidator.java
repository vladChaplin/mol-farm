package com.mol_ferma.web.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneNumberConstraintsValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private static final Pattern KAZAKHSTAN_PHONE_PATTERN = Pattern.compile("(?:\\+77|87)\\d{9}");

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if(phoneNumber == null || phoneNumber.isEmpty()) return false;

        return KAZAKHSTAN_PHONE_PATTERN.matcher(phoneNumber).matches();
    }
}
