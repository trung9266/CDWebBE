package com.example.cdwebbe.controller;

import com.example.cdwebbe.model.Movie;
import com.example.cdwebbe.payload.MovieRequest;
import com.example.cdwebbe.repository.MovieRepository;
import com.example.cdwebbe.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);
    @Autowired
    MovieService movieService;
    @Autowired
    MovieRepository movieRepository;

    @GetMapping("/{slug}")
    public ResponseEntity<Movie> getMovieBySlug(@PathVariable(value = "slug") Long slug){
        Movie result = movieService.getMovieBySlug(slug);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/addmovie")
    public ResponseEntity addMovie(@RequestBody MovieRequest movieRequest){
        Movie result = movieService.addMovie(movieRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body("Movie Created Successfully");
    }
    @GetMapping("/getall")
    public ResponseEntity getAllMovie(){
        List<Movie> result = movieService.getAllMovie();
        return ResponseEntity.ok(result);
    }
}
