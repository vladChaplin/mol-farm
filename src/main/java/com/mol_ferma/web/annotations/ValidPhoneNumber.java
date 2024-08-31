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
@Constraint(validatedBy = PhoneNumberConstraintsValidator.class)
public @interface ValidPhoneNumber {
    String message() default "Некорректный формат номера телефона";
    Class<?>[] groups() default {};
    Class<?extends Payload> [] payload()default {};
}
