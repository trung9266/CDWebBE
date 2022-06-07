package com.example.cdwebbe.service.impl;

import com.example.cdwebbe.model.Movie;
import com.example.cdwebbe.payload.MovieRequest;
import com.example.cdwebbe.repository.MovieRepository;
import com.example.cdwebbe.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Override
    public Movie getMovieBySlug(Long slug) {
        return movieRepository.findBySlug(slug);
    }

    @Override
    public List<Movie> getAllMovie() {
        List<Movie> result =  movieRepository.findAll();
        return result;
    }

    @Override
    public Movie addMovie(MovieRequest movie) {
        boolean isExist = movieRepository.existsBySlug(movie.getSlug());
        if (!isExist) {
            Movie mv = new Movie();
            mv.setSlug(movie.getSlug());
            mv.setUrl(movie.getUrl());
            return movieRepository.save(mv);
        }
        return null;
    }


}
