package com.movieflix.repository;

import com.movieflix.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findMovieByCategoriesId(Long categoryId);
}
