package com.example.cdwebbe.payload;

import com.example.cdwebbe.model.Movie;
import com.example.cdwebbe.model.WishList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WishListItemResponse {
    private Movie movie ;
    private WishList wishList ;
}
