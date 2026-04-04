package com.movieflix.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public record MovieRequest(@Schema(type = "string", description = "Movie name")
                           @NotEmpty(message = "The movie tittle can not be null")
                           String title,
                           @Schema(type = "string", description = "Movie description")
                           String description,
                           @Schema(type = "date", description = "Movie launch day in 'dd/MM/yyyy' format. ex: 15/01/2026")
                           @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                           LocalDate releaseDate,
                           @Schema(type = "double", description = "Movie score note from 0.0 to 10.0")
                           double rating,
                           @Schema(type = "array", description = "List of categories the movie belongs to")
                           List<Long> categories,
                           @Schema(type = "string", description = "list of the streamings plataforms where you can watch the movie")
                           List<Long> streamings
                            ) {
}
