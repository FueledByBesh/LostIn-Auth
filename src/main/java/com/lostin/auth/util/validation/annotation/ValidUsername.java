package com.lostin.auth.util.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

@NotBlank(message = "Username required")
@Size(min = 3, max = 20, message = "Username should be from 3 to 20 symbols")
@Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can contain only letters, numbers and _")
public @interface ValidUsername {

    String message() default "Username is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
