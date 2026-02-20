package com.lostin.auth.util.validation.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

@NotBlank(message = "Email is required")
@Email(message = "Email is not valid")
public @interface ValidEmail {

    String message() default "Email is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
