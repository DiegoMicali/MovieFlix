package com.movieflix.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @Email
        @NotBlank(message = "Email can not be null")
        String email,
        @NotBlank(message = "Password can not be null")
        String password) {
}
