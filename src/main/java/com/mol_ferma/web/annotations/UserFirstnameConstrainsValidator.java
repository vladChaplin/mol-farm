package com.mol_ferma.web.annotations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class UserFirstnameConstrainsValidator implements ConstraintValidator<ValidUserFirstname, String> {
    private static final Pattern FIRSTNAME_PATTERN = Pattern.compile("^[A-Za-zА-Яа-яЁё]+$");

    @Override
    public void initialize(ValidUserFirstname constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if(name == null || name.isEmpty()) return false;

        return FIRSTNAME_PATTERN.matcher(name).matches();
    }
}
