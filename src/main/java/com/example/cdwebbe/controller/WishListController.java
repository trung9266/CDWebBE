package com.example.cdwebbe.controller;

import com.example.cdwebbe.exception.NotFoundException;
import com.example.cdwebbe.model.*;
import com.example.cdwebbe.payload.ResponseObject;
import com.example.cdwebbe.payload.WishListItemResponse;
import com.example.cdwebbe.payload.WishListRequest;
import com.example.cdwebbe.security.CurrentUser;
import com.example.cdwebbe.security.UserPrincipal;
import com.example.cdwebbe.service.IWishListService;
import com.example.cdwebbe.service.MovieService;
import com.example.cdwebbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;


@RestController
@RequestMapping("api/wishlist")
public class WishListController {
    @Autowired
    IWishListService iWishListService;
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper mapper;
    @Autowired
    MovieService movieService;
    @PostMapping("/addWishList/{slug}")
    public ResponseEntity<?> addWishList(@PathVariable(value = "slug") String slug,@RequestBody WishListRequest info) {
        Movie toCartResponse = iWishListService.addMovieWishList(info.getId(),slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK.value(), "successful!", toCartResponse));

    }
    @PostMapping("/findAll")
    public ResponseEntity<?> findAll(@RequestParam(value = "action") String action, @RequestParam(required = false ) String favProductId,
                                     @RequestParam(defaultValue ="0" ) int pageIndex, @RequestParam(defaultValue = "10") int pageSize,@RequestBody WishListRequest info){

        User user = this.userService.findById(info.getId());
        WishList wishList = this.iWishListService.findByUser(user);
        Specification<WishListItem> spec = new Specification<WishListItem>() {
            @Override
            public Predicate toPredicate(Root<WishListItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("wishList"),wishList);
            }
        };
        Pageable pageable =  PageRequest.of(pageIndex,pageSize);
        List<WishListItem> wishListItem = null;
        if(action.equals("show")){
            wishListItem = this.iWishListService.findAll(spec,  pageable);
        }else if(action.equals("remove")){
            Movie movie = this.movieService.getMovieBySlug(favProductId);
            this.iWishListService.delete(movie);
            wishListItem = this.iWishListService.findAll(spec,  pageable);
        }
        List<WishListItemResponse> list = new ArrayList<>();
        for (WishListItem wishListItemEntity :wishListItem) {
            list.add(this.mapper.map(wishListItemEntity,WishListItemResponse.class));

        }
return ResponseEntity.ok(list);
    }

}
