package com.mol_ferma.web.annotations;

import com.mol_ferma.web.utils.service.ValidationLoggingService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class DuplicatePasswordConstraint implements ConstraintValidator<ValidDuplicatePassword, String> {

    @Autowired
    private ValidationLoggingService validationLoggingService;

    @Override
    public void initialize(ValidDuplicatePassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        var currentPassword = validationLoggingService.getValidationValue();

        if(currentPassword == null || password == null) return false;

        return password.equals(currentPassword);
    }
}
