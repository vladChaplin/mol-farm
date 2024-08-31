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
@Constraint(validatedBy = UserFirstnameConstrainsValidator.class)
public @interface ValidUserFirstname {
    String message() default "В имени или фамилии должны быть только латинские буквы или кириллицы";
    Class<?>[] groups() default {};
    Class<?extends Payload> [] payload()default {};
}
