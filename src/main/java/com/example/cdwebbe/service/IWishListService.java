package com.example.cdwebbe.service;

import com.example.cdwebbe.model.Movie;
import com.example.cdwebbe.model.User;
import com.example.cdwebbe.model.WishList;
import com.example.cdwebbe.model.WishListItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IWishListService {
    Movie addMovieWishList(Long id, Long id1);

    List<WishListItem> findAll(Specification<WishListItem> spec, Pageable pageable);
    WishList findByUser(User user);

    void delete(Movie movie);
}
