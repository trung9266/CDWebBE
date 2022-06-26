package com.example.cdwebbe.service.impl;

import com.example.cdwebbe.model.Genres;
import com.example.cdwebbe.model.Movie;
import com.example.cdwebbe.model.ProductionCompany;
import com.example.cdwebbe.model.ProductionCountries;
import com.example.cdwebbe.payload.MovieRequest;
import com.example.cdwebbe.payload.MovieResponse;
import com.example.cdwebbe.repository.GenresRepository;
import com.example.cdwebbe.repository.MovieRepository;
import com.example.cdwebbe.service.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    GenresRepository genresRepository;
    @Autowired
    ModelMapper mapper;

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

    @Override
    public List<Movie> listAll(String keyword) {
        if (keyword != null) {
            return movieRepository.search(keyword);
        }
        return movieRepository.findAll();
    }

    @Override
    public Map<String ,Object> showAndSearchProduct(String searchValue, Pageable pageable) {

        Page<Movie> pageTuts;
        if(searchValue.equals("all")) {
            pageTuts = this.movieRepository.findAll(pageable);
        }else {
            pageTuts = this.movieRepository.findByTitleContainingIgnoreCase(searchValue, pageable);
        }
        List<Movie> productEntityList = pageTuts.getContent();
        List<MovieResponse> MovieResponse = this.covertProductEntityToResponse(productEntityList);
        Map<String,Object> result = new HashMap<>();
        result.put("products",MovieResponse);
        result.put("curerentPage",pageTuts.getNumber());
        result.put("totalitems",pageTuts.getTotalElements());
        result.put("totalPage",pageTuts.getTotalPages());
        return result ;
    }

    @Override
    public String deleteMovie(long id) {
        boolean isExist = movieRepository.existsById(id);
        if(isExist){
            movieRepository.deleteById(id);
            return "Delete movie success";
        }else{
            return "Movie is not exist";
        }
    }

    @Override
    public Movie save(Movie m) {
        return movieRepository.save(m);
    }

    public List<MovieResponse> covertProductEntityToResponse(List<Movie> productDetailEntities){
        List<MovieResponse> responseList = new ArrayList<>();
        for (Movie productEntity :productDetailEntities) {

            responseList.add(this.mapper.map(productEntity,MovieResponse.class));
            System.out.println(productDetailEntities);

        }
        return  responseList;
    }

}
