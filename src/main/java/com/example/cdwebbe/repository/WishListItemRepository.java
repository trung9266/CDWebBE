package com.example.cdwebbe.repository;

import com.example.cdwebbe.model.Movie;
import com.example.cdwebbe.model.WishList;
import com.example.cdwebbe.model.WishListItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WishListItemRepository extends JpaRepository<WishListItem, Long>, JpaSpecificationExecutor<WishListItem> {
    WishListItem findByWishListAndMovie(WishList wishList, Movie movie);

    Page findAll(Specification spec, Pageable pageable);
    @Transactional
    void deleteByMovie(Movie movie);
}
