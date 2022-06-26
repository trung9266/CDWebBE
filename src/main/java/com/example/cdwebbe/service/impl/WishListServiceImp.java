package com.example.cdwebbe.service.impl;

import com.example.cdwebbe.model.Movie;
import com.example.cdwebbe.model.User;
import com.example.cdwebbe.model.WishList;
import com.example.cdwebbe.model.WishListItem;
import com.example.cdwebbe.repository.MovieRepository;
import com.example.cdwebbe.repository.UserRepository;
import com.example.cdwebbe.repository.WishListItemRepository;
import com.example.cdwebbe.repository.WishListRepository;
import com.example.cdwebbe.service.IWishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListServiceImp implements IWishListService {
    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    WishListItemRepository wishListItemRepository;

    @Override
    public Movie addMovieWishList(Long id, String slug) {
        User user = this.userRepository.findById(id).get();
        WishList wishList = this.wishListRepository.findByUser(user);
        if (wishList == null) {
            wishList = new WishList(user);
            this.wishListRepository.save(wishList);
        }
        Movie movie = this.movieRepository.findBySlug(slug);
        WishListItem wishListItem = this.wishListItemRepository.findByWishListAndMovie(wishList, movie);
        if (wishListItem != null) {
            return null;
        } else {
            wishListItem = new WishListItem(movie, wishList);
            this.wishListItemRepository.save(wishListItem);
        }
        return movie;
    }


    @Override
    public List<WishListItem> findAll(Specification<WishListItem> spec, Pageable pageable) {
        return this.wishListItemRepository.findAll(spec,pageable).getContent();
    }

    @Override
    public WishList findByUser(User user) {
        return this.wishListRepository.findByUser(user);
    }

    @Override
    public void delete(Movie movie) {
        this.wishListItemRepository.deleteByMovie(movie);

    }
}
