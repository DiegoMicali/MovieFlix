package com.movieflix.request;


import jakarta.validation.constraints.NotEmpty;

public record UserRequest(@NotEmpty(message = "Name can not be null")
                          String name,
                          @NotEmpty(message = "Email can not be null")
                          String email,
                          @NotEmpty(message = "Password can not be null")
                          String password) {

}
