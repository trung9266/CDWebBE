package com.example.cdwebbe.controller;

import com.example.cdwebbe.exception.NotFoundException;
import com.example.cdwebbe.model.Movie;
import com.example.cdwebbe.model.User;
import com.example.cdwebbe.model.WishList;
import com.example.cdwebbe.model.WishListItem;
import com.example.cdwebbe.payload.ResponseObject;
import com.example.cdwebbe.payload.WishListItemResponse;
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
    @PostMapping("/addWishList")
    public ResponseEntity<?> addWishList(@RequestParam (value =  "idMovie" ) Long id) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            throw new NotFoundException("please login to purchase!");
        }
        UserPrincipal userDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Movie toCartResponse = iWishListService.addMovieWishList(userDetails.getId(),id);
//        Movie toCartResponse = iWishListService.addMovieWishList(id1,id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK.value(), "successful!", toCartResponse));

    }
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(@RequestParam String action,@RequestParam(required = false ) Long favProductId,
             @RequestParam(defaultValue ="0" ) int pageIndex,@RequestParam(defaultValue = "10") int pageSize){
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            throw new NotFoundException("please login to purchase!");
        }
        UserPrincipal userDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.findById(userDetails.getId());
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
            Movie movie = this.movieService.findByIdMovie(favProductId);
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
