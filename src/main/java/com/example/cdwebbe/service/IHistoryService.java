package com.example.cdwebbe.service;

import com.example.cdwebbe.model.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IHistoryService {
    Movie addMovieHistory(Long id, String slug);

    List<HistoryItem> findAll(Specification<HistoryItem> spec, Pageable pageable);
    History findByUser(User user);

    void delete(Movie movie);
}
