package com.lostin.auth.util.interfaces;

import com.lostin.auth.exception.ValidationException;

import java.util.Optional;

public interface Validatable {
    void validate() throws ValidationException;
    Optional<String> getViolations();
}

