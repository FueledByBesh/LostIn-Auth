package com.lostin.auth.request_response.basic_auth_flow.request;

import jakarta.validation.constraints.*;

public record BasicAuthLoginRequest(
        @NotNull(message = "Email is required")
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email")
        String email,

        @NotNull(message = "Password is required")
        @NotBlank(message = "Password is required")
        @Size(min = 8,max = 20,message = "Password must be between 8 and 20 characters")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
                message = "Password must contain at least one number, one uppercase letter, one lowercase letter and one symbol"
        )
        String password
) {
}
