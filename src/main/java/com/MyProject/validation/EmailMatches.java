package com.MyProject.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = EmailMatchesValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailMatches {
	
	String message()default "Az emailcím nem egyezik!";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}
