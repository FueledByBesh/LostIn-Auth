package com.lostin.auth.util.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER,ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)

@NotBlank(message = "Password is required")
@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
@Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
        message = "Password must contain at least one number, one uppercase letter, one lowercase letter and one symbol"
)
public @interface ValidPassword {

    String message() default "UUID is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
