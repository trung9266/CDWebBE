package com.example.cdwebbe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Max;
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
    private String slug;
    @NotNull
    private String title;
    @NotNull
    @Column(name = "poster_path")
    private String posterUrl;
    @NotNull
    @Column(name = "backdrop_path")
    private String backdropUrl;
    @OneToMany(
            mappedBy = "movie",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    private List<Genres> genres = new ArrayList<>();
    @Column(name = "release_date")
    private String releaseDate;
    @Lob
    @Column(name = "overview")
    private String overview;
    @OneToMany(
            mappedBy = "movie",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    private List<ProductionCompany> productionCompanies = new ArrayList<>();
    @OneToMany(
            mappedBy = "movie",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    private List<ProductionCountries> productionCountries = new ArrayList<>();
    @NotNull
    private String url;
    @JsonIgnore
    @OneToMany(mappedBy = "movie")
    private List<WishListItem> wishListItems;
    @JsonIgnore
    @OneToMany(mappedBy = "movie")
    private List<HistoryItem> historyItem;


    public Movie(String slug, String title, String posterUrl, String backdropUrl, List<Genres> genres, String releaseDate, String overview, List<ProductionCompany> productionCompanies, List<ProductionCountries> productionCountries, String url) {
        this.slug = slug;
        this.title = title;
        this.posterUrl = posterUrl;
        this.backdropUrl = backdropUrl;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.productionCompanies = productionCompanies;
        this.productionCountries = productionCountries;
        this.url = url;
    }

    public Movie() {

    }
    public void addGenres(Genres gen) {
        genres.add(gen);
        gen.setMovie(this);
    }
    public void addProductionCompanies(ProductionCompany company) {
        productionCompanies.add(company);
        company.setMovie(this);
    }
    public void addProductionCountries(ProductionCountries countries) {
        productionCountries.add(countries);
        countries.setMovie(this);
    }

    public void removeGenres(Genres gen) {
        genres.remove(gen);
        gen.setMovie(null);
    }

}