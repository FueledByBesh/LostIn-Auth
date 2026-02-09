package com.lostin.auth.util.abstracts;

import com.lostin.auth.exception.ValidationException;

import java.util.Optional;

public interface Validatable {
    void validate() throws ValidationException;
    Optional<String> getViolations();
}

