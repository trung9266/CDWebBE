package com.example.cdwebbe.service;

import com.example.cdwebbe.model.Movie;
import com.example.cdwebbe.payload.MovieRequest;

import java.util.List;

public interface MovieService{
    Movie getMovieBySlug(String slug);
    List<Movie> getAllMovie();

    Movie createMovie(MovieRequest movieRequest);
    Movie findByIdMovie(long id);
}
