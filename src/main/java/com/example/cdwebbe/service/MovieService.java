package com.example.cdwebbe.service;

import com.example.cdwebbe.model.Movie;
import com.example.cdwebbe.model.User;
import com.example.cdwebbe.payload.MovieRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface MovieService{
    Movie getMovieBySlug(String slug);
    List<Movie> getAllMovie();

    Movie createMovie(MovieRequest movieRequest);
    Movie findByIdMovie(long id);
    List<Movie> listAll(String keyword);
    Map<String,Object> showAndSearchProduct(String searchValue, Pageable pageable);

    String deleteMovie(long id);
    Movie save(Movie m);

}
