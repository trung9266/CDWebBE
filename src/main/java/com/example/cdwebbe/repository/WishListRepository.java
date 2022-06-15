package com.example.cdwebbe.repository;


import com.example.cdwebbe.model.User;
import com.example.cdwebbe.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> , JpaSpecificationExecutor {
    WishList findByUser(User user);
}
