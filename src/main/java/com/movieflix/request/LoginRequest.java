package com.movieflix.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @Email
        @NotEmpty(message = "Email can not be null")
        String email,
        @NotEmpty(message = "Password can not be null")
        String password) {
}
