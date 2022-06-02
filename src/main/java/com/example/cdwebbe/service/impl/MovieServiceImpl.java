package com.example.cdwebbe.service.impl;

import com.example.cdwebbe.model.Movie;
import com.example.cdwebbe.repository.MovieRepository;
import com.example.cdwebbe.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Override
    public Movie getMovieById(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.get();
    }

    @Override
    public List<Movie> getAllMovie() {
        List<Movie> result =  movieRepository.findAll();
        return result;
    }
}
