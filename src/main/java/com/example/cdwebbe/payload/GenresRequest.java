package com.example.cdwebbe.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Getter
@Setter
public class GenresRequest {
    @NotBlank
    @Size(max = 40)
    private String name;

}
