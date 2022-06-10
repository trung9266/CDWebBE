package com.example.cdwebbe.payload;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.Nullable;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@Getter
@Setter
public class MovieRequest {
    @NotNull
    private Long slug;
    @NotNull
    private String title;
    @Nullable
    private String posterUrl;
    @Nullable
    private String backdropUrl;
    @Nullable
    private List<String> genres;
    @Nullable
    private String releaseDate;
    @Nullable
    private String overview;
    @Nullable
    private List<String> productionCompanies;
    @Nullable
    private List<String> productionCountries;
    @NotNull
    private String url;


}
