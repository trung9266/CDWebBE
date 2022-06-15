package com.example.cdwebbe.model;

import com.example.cdwebbe.model.audit.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wishListItem")
public class WishListItem extends BaseEntity {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "movie_id",referencedColumnName = "id")
    private Movie movie ;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "wishListId",referencedColumnName = "id")
    private WishList wishList ;
}
