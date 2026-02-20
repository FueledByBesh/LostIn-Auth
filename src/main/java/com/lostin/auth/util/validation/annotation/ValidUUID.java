package com.lostin.auth.util.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER,ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)

@NotNull(message = "UUID is required")
@UUID(message = "UUID is not valid",allowNil = false)
public @interface ValidUUID {

    String message() default "UUID is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
