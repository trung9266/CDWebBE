package com.example.cdwebbe.controller;

import com.example.cdwebbe.model.Movie;
import com.example.cdwebbe.security.CurrentUser;
import com.example.cdwebbe.security.UserPrincipal;
import com.example.cdwebbe.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);
    @Autowired
    MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity getMovieById(@PathVariable(name = "id") Long id){
        Movie result = movieService.getMovieById(id);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getall")
    public ResponseEntity getAllMovie(){
        List<Movie> result = movieService.getAllMovie();
        return ResponseEntity.ok(result);
    }
}
