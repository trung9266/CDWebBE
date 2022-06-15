package com.example.cdwebbe.service.impl;

import com.example.cdwebbe.model.Genres;
import com.example.cdwebbe.model.Movie;
import com.example.cdwebbe.model.ProductionCompany;
import com.example.cdwebbe.model.ProductionCountries;
import com.example.cdwebbe.payload.MovieRequest;
import com.example.cdwebbe.repository.GenresRepository;
import com.example.cdwebbe.repository.MovieRepository;
import com.example.cdwebbe.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    GenresRepository genresRepository;

    @Override
    public Movie getMovieBySlug(String slug) {
        return movieRepository.findBySlug(slug);
    }

    @Override
    public List<Movie> getAllMovie() {
        List<Movie> result =  movieRepository.findAll();
        return result;
    }

    @Override
    public Movie createMovie(MovieRequest movieRequest) {
        boolean isExist = movieRepository.existsBySlug(movieRequest.getSlug());
        if (!isExist) {
            Movie mv = new Movie();
            mv.setSlug(movieRequest.getSlug());
            mv.setTitle(movieRequest.getTitle());
            mv.setPosterUrl(movieRequest.getPosterUrl());
            mv.setBackdropUrl(movieRequest.getBackdropUrl());
            movieRequest.getGenres().forEach(genresRequest -> {
                mv.addGenres(new Genres(genresRequest.getName()));
            });
            mv.setReleaseDate(movieRequest.getReleaseDate());
            mv.setOverview(movieRequest.getOverview());
            movieRequest.getProductionCompanies().forEach(companyRequest -> {
                mv.addProductionCompanies(new ProductionCompany(companyRequest.getName()));
            });
            movieRequest.getProductionCountries().forEach(countriesRequest -> {
                mv.addProductionCountries(new ProductionCountries(countriesRequest.getName()));
            });
            mv.setUrl(movieRequest.getUrl());
            return movieRepository.save(mv);
        }
        return null;
    }

    @Override
    public Movie findByIdMovie(long id) {
        return this.movieRepository.findById(id).get();
    }

}
