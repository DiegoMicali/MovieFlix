package com.movieflix.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UserRequest(@NotBlank(message = "Name can not be null")
                          String name,
                          @Email
                          @NotBlank(message = "Email can not be null")
                          String email,
                          @NotBlank(message = "Password can not be null")
                          String password) {

}
