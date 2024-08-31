package com.mol_ferma.web.annotations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordContraintsValidator implements ConstraintValidator<ValidPassword, String> {

//  TODO:  Can be impemented via a library PASSAY
    private static final int MIN_LENGHT = 8;
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if(password == null) return false;

        if (password.length() < MIN_LENGHT) return false;

        if(!UPPERCASE_PATTERN.matcher(password).find()) return false;

        if(!LOWERCASE_PATTERN.matcher(password).find()) return false;

        if(!DIGIT_PATTERN.matcher(password).find()) return false;

        return true;
    }
}
