package com.mol_ferma.web.annotations;

import com.mol_ferma.web.dto.ForgotPasswordForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class PasswordMatchesConstraint implements ConstraintValidator<ValidPasswordMatches, ForgotPasswordForm> {

    @Override
    public void initialize(ValidPasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ForgotPasswordForm passwordForm, ConstraintValidatorContext context) {

        if(passwordForm.getPassword() == null || passwordForm.getDuplicatePassword() == null) return false;

        var isEqual = passwordForm.getPassword().equals(passwordForm.getDuplicatePassword());
        if(!isEqual) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Пароли должны совпадать!")
                    .addConstraintViolation();
        }

        return isEqual;
    }
}
