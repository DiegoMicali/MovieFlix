package com.movieflix.controller;

import com.movieflix.entity.Movie;
import com.movieflix.mapper.MovieMapper;
import com.movieflix.request.MovieRequest;
import com.movieflix.response.MovieResponse;
import com.movieflix.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movieflix/movie")
@RequiredArgsConstructor
@Tag(name = "Movie", description = "Resource responsable for management of movies")
public class MovieController {

    private final MovieService movieService;

    @Operation(summary = "Get Movies", description = "Method to get a list of all movies",
        security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Successfully found all movies",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovieResponse.class))))
    @GetMapping
    public ResponseEntity<List<MovieResponse>> findAll() {
       return ResponseEntity.ok(movieService.findAll()
               .stream()
               .map(MovieMapper::toMovieResponse)
               .toList());
    }

    @Operation(summary = "Get Movies by Id", description = "Method to get a movie by Id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Successfully found the movie",
            content = @Content(schema = @Schema(implementation = MovieResponse.class)))
    @ApiResponse(responseCode = "404", description = "Movie not finded", content = @Content())
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findById(@PathVariable Long id) {
        return movieService.findById(id)
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Save Movie", description = "Method to save a new movie",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Movie saved successfully",
        content = @Content(schema = @Schema(implementation = MovieResponse.class)))
    @PostMapping
    public ResponseEntity<MovieResponse> save(@Valid @RequestBody MovieRequest movieResquest) {
        Movie savedMovie = movieService.save(MovieMapper.toMovie(movieResquest));
        return ResponseEntity.ok(MovieMapper.toMovieResponse(savedMovie));
    }

    @Operation(summary = "Update a movie", description = "Method to update a movie by Id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Successfully updated the movie",
            content = @Content(schema = @Schema(implementation = MovieResponse.class)))
    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> update(@PathVariable Long id, @Valid @RequestBody MovieRequest movieRequest) {
        return movieService.update(id, MovieMapper.toMovie(movieRequest))
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Movies by Id", description = "Method to delete a movie by Id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Successfully deleted the movie", content = @Content())
    @ApiResponse(responseCode = "404", description = "Movie not finded", content = @Content())
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieById(@PathVariable Long id) {
        Optional<Movie> optMovie = movieService.findById(id);
        if (optMovie.isPresent()) {
            movieService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get Movie by category", description = "Method to get movies by category",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Successfully found movies by category",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovieResponse.class))))
    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> findByCategory(@RequestParam Long category) {
        return ResponseEntity.ok(movieService.findByCategory(category)
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList());
    }

}

