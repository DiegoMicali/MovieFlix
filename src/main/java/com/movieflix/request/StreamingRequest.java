package com.movieflix.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record StreamingRequest(@Schema(type = "string", description = "Streaming name")
                               @NotEmpty(message = "The streaming plataform can not be null")
                               String name) {
}
