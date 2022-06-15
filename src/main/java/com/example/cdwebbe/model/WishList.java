package com.example.cdwebbe.model;


import com.example.cdwebbe.model.audit.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wishList")
public class WishList extends BaseEntity {
    @JsonIgnore
    @OneToMany(mappedBy = "wishList")
    private List<WishListItem> wishListItems;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID", referencedColumnName = "id")
    private User user;

    public WishList(User user) {
        this.user = user;
    }
}
