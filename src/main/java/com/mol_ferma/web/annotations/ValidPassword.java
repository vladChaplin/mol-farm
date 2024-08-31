package com.mol_ferma.web.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordContraintsValidator.class)
public @interface ValidPassword {
    String message() default "Пароль должен быть сложным";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
}
