package com.example.cdwebbe.payload;

import com.example.cdwebbe.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class WishListRequest {
    @NotNull
    private Long id;
}
