package com.mol_ferma.web.annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DuplicatePasswordConstraint.class)
public @interface ValidDuplicatePassword {
    String message() default "Пароли должны совпадать!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
