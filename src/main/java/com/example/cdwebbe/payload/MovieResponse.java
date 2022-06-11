package com.example.cdwebbe.payload;

import com.example.cdwebbe.model.Genres;
import com.example.cdwebbe.model.ProductionCompany;
import com.example.cdwebbe.model.ProductionCountries;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class MovieResponse {
    private String slug;
    private String title;
    private String posterUrl;
    private String backdropUrl;
    private List<Genres> genres;
    private String releaseDate;
    private String overview;
    private List<ProductionCompany> productionCompanies;
    private List<ProductionCountries> productionCountries;
    private String url;
}
