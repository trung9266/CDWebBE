package com.example.cdwebbe.controller;

import com.example.cdwebbe.model.*;
import com.example.cdwebbe.payload.MovieRequest;
import com.example.cdwebbe.payload.ResponseObject;
import com.example.cdwebbe.repository.MovieRepository;
import com.example.cdwebbe.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> createMovie(@Valid @RequestBody MovieRequest movieRequest) {
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
    @GetMapping("/search")
    public ResponseEntity viewHomePage(@RequestParam(value = "keyword") String keyword) {
        List<Movie> listProducts = movieService.listAll(keyword);
        return ResponseEntity.ok(listProducts);
    }
    @DeleteMapping("/deleteMovie/{id}")
    public ResponseEntity deleteMovie(
            @PathVariable long id
    ) {
        String message = movieService.deleteMovie(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete movie success");
    }
    @PutMapping("/update/{slug}")
    public Movie updateMovie(@PathVariable(value = "slug") String slug, @RequestBody Movie m){
        Movie movie = movieService.getMovieBySlug(slug);

        if(movie!=null){
            movie.setSlug(m.getSlug());
            movie.setTitle(m.getTitle());
            movie.setBackdropUrl(m.getBackdropUrl());
            movie.setPosterUrl(m.getPosterUrl());
            movie.getGenres().forEach(genresRequest -> {
                m.addGenres(new Genres(genresRequest.getName()));
            });
            movie.setReleaseDate(m.getReleaseDate());
            movie.setOverview(m.getOverview());
            movie.getProductionCompanies().forEach(companyRequest -> {
                m.addProductionCompanies(new ProductionCompany(companyRequest.getName()));
            });
            movie.getProductionCountries().forEach(countriesRequest -> {
                m.addProductionCountries(new ProductionCountries(countriesRequest.getName()));
            });
            movie.setUrl(m.getUrl());
            return movieService.save(movie);
        }
        return null;
    }
    @GetMapping("/ShowAndsearch")
    public ResponseEntity<?> showAndsearchProductEntity(@RequestParam(required = true,value = "keyword") String searchValue,
                                                        @RequestParam(defaultValue = "0") int pageIndex,  @RequestParam(defaultValue = "5") int pageSize){

        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        Map<String,Object> result= this.movieService.showAndSearchProduct(searchValue,pageable);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "no product in db", ""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "successly!", result));




    }

}
