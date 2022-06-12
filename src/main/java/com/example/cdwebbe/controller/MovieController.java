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

import javax.validation.Valid;
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
    public ResponseEntity<Movie> getMovieBySlug(@PathVariable(value = "slug") String slug){
        Movie result = movieService.getMovieBySlug(slug);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getall")
    public ResponseEntity getAllMovie(){
        List<Movie> result = movieService.getAllMovie();
        return ResponseEntity.ok(result);
    }
    @PostMapping("/addmovie")
    public ResponseEntity<?> createPoll(@Valid @RequestBody MovieRequest movieRequest) {
        boolean isExist = movieRepository.existsBySlug(movieRequest.getSlug());
        if (!isExist){
            Movie movie = movieService.createMovie(movieRequest);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{movieId}")
                    .buildAndExpand(movie.getId()).toUri();

            return ResponseEntity.created(location)
                    .body("Movie Created Successfully");
        }
        return ResponseEntity.ok("Movie is exist");
    }
}
