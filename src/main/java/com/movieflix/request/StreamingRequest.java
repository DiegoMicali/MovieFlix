package com.movieflix.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record StreamingRequest(@NotEmpty(message = "Nome do serviço de streaming é obrigatório") String name) {
}
