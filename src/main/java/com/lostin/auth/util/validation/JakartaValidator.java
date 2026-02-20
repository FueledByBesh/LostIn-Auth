package com.lostin.auth.util.validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class JakartaValidator {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static Validator validator(){
        return validator;
    }

    public static ValidatorFactory factory(){
        return factory;
    }
}
