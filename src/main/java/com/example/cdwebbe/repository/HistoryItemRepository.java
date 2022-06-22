package com.example.cdwebbe.repository;

import com.example.cdwebbe.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface HistoryItemRepository extends JpaRepository<HistoryItem, Long>, JpaSpecificationExecutor<HistoryItem> {
    HistoryItem findByHistoryAndMovie(History history, Movie movie);

    Page findAll(Specification spec, Pageable pageable);
    @Transactional
    void deleteByMovie(Movie movie);
}
