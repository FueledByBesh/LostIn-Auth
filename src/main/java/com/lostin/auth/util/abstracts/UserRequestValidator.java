package com.lostin.auth.util.abstracts;

import com.lostin.auth.exception.BadRequestException;

public interface UserRequestValidator {
    void validate() throws BadRequestException;
}
