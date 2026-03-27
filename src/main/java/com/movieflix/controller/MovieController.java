package com.movieflix.controller;

import com.movieflix.entity.Movie;
import com.movieflix.mapper.MovieMapper;
import com.movieflix.request.MovieRequest;
import com.movieflix.response.MovieResponse;
import com.movieflix.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieflix/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieResponse>> findAll() {
       return ResponseEntity.ok(movieService.findAll()
               .stream()
               .map(MovieMapper::toMovieResponse)
               .toList());
    }

    @PostMapping
    public ResponseEntity<MovieResponse> save(@RequestBody MovieRequest movieResquest) {
        Movie savedMovie = movieService.save(MovieMapper.toMovie(movieResquest));
        return ResponseEntity.ok(MovieMapper.toMovieResponse(savedMovie));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findById(@PathVariable Long id) {
        return movieService.findById(id)
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieById(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

