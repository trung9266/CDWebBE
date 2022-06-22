package com.example.cdwebbe.repository;


import com.example.cdwebbe.model.History;
import com.example.cdwebbe.model.User;
import com.example.cdwebbe.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> , JpaSpecificationExecutor {
    History findByUser(User user);
}
