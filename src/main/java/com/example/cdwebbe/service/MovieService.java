package com.example.cdwebbe.service;

import com.example.cdwebbe.model.Movie;

import java.util.List;

public interface MovieService{
    Movie getMovieById(Long id);
    List<Movie> getAllMovie();
}
