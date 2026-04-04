package com.movieflix.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record CategoryRequest(@Schema(type = "string", description = "Category name")
                              @NotEmpty(message = "The category can not be null")
                              String name) {

}
