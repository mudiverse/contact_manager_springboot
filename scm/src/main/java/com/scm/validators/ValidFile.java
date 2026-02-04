package com.scm.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ElementType.FIELD,ElementType.PARAMETER,ElementType.METHOD,ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR}) 
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Constraint(validatedBy=FileValidator.class)
public @interface ValidFile {
    String message() default "Invalid file uploaded";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
