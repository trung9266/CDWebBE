package com.example.cdwebbe.controller;

import com.example.cdwebbe.model.*;
import com.example.cdwebbe.payload.ResponseObject;
import com.example.cdwebbe.payload.HistoryItemResponse;
import com.example.cdwebbe.payload.WishListRequest;
import com.example.cdwebbe.service.IHistoryService;
import com.example.cdwebbe.service.MovieService;
import com.example.cdwebbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;


@RestController
@RequestMapping("api/history")
public class HistoryController {
    @Autowired
    IHistoryService iHistoryService;
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper mapper;
    @Autowired
    MovieService movieService;
    @PostMapping("/addHistory/{slug}")
    public ResponseEntity<?> addHistory(@PathVariable(value = "slug") String slug,@RequestBody WishListRequest info) {
        Movie history = iHistoryService.addMovieHistory(info.getId(),slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK.value(), "successful!", history));
    }
    @PostMapping("/findAll")
    public ResponseEntity<?> findAll(@RequestParam(value = "action") String action, @RequestParam(required = false ) String favProductId,
                                     @RequestParam(defaultValue ="0" ) int pageIndex, @RequestParam(defaultValue = "10") int pageSize,@RequestBody WishListRequest info){

        User user = this.userService.findById(info.getId());
        History history = this.iHistoryService.findByUser(user);
        Specification<HistoryItem> spec = new Specification<HistoryItem>() {
            @Override
            public Predicate toPredicate(Root<HistoryItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("history"),history);
            }
        };
        Pageable pageable =  PageRequest.of(pageIndex,pageSize);
        List<HistoryItem> historyItem = null;
        if(action.equals("show")){
            historyItem = this.iHistoryService.findAll(spec,  pageable);
        }else if(action.equals("remove")){
            Movie movie = this.movieService.getMovieBySlug(favProductId);
            this.iHistoryService.delete(movie);
            historyItem = this.iHistoryService.findAll(spec,  pageable);
        }
        List<HistoryItemResponse> list = new ArrayList<>();
        for (HistoryItem historyItemEntity :historyItem) {
            list.add(this.mapper.map(historyItemEntity,HistoryItemResponse.class));
        }
        return ResponseEntity.ok(list);
    }


}
