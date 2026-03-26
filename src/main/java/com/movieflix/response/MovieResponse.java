package com.movieflix.response;

import lombok.Builder;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

@Builder
public record MovieResponse(Long id,
                            String title,
                            String description,
                            LocalDate releaseDate,
                            double rating,
                            List<CategoryResponse> categories,
                            List<StreamingResponse> streaming
                            ) {
}
