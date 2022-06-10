package com.example.cdwebbe.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long slug;
    @NotNull
    private String title;
    @NotNull
    @Column(name = "poster_path")
    private String posterUrl;
    @NotNull
    @Column(name = "backdrop_path")
    private String backdropUrl;
    @ElementCollection
    @CollectionTable(name = "genres", joinColumns = @JoinColumn(name = "slug"))
    @Column(name = "name")
    private List<String> genres;
    @Column(name = "release_date")
    private String releaseDate;
    @Column(name = "overview")
    private String overview;
    @ElementCollection
    @CollectionTable(name = "production_companies", joinColumns = @JoinColumn(name = "slug"))
    @Column(name = "name")
    private List<String> productionCompanies;
    @ElementCollection
    @CollectionTable(name = "production_countries", joinColumns = @JoinColumn(name = "slug"))
    @Column(name = "name")
    private List<String> productionCountries;
    @NotNull
    private String url;


    public Movie(Long id, Long slug, String title, String posterUrl, List<String> genres, String releaseDate, String overview, List<String> productionCompanies, List<String> productionCountries, String url) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.posterUrl = posterUrl;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.productionCompanies = productionCompanies;
        this.productionCountries = productionCountries;
        this.url = url;
    }

    public Movie() {

    }
}