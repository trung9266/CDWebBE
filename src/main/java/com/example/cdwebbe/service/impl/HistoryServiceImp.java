package com.example.cdwebbe.service.impl;

import com.example.cdwebbe.model.*;
import com.example.cdwebbe.repository.*;
import com.example.cdwebbe.service.IHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImp implements IHistoryService {
    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    HistoryItemRepository historyItemRepository;

    @Override
    public Movie addMovieHistory(Long id, String slug) {
        User user = this.userRepository.findById(id).get();
        History history = this.historyRepository.findByUser(user);
        if (history == null) {
            history = new History(user);
            this.historyRepository.save(history);
        }
        Movie movie = this.movieRepository.findBySlug(slug);
        HistoryItem historyItem = this.historyItemRepository.findByHistoryAndMovie(history, movie);
        if (historyItem != null) {
            return null;
        } else {
            historyItem = new HistoryItem(movie, history);
            this.historyItemRepository.save(historyItem);
        }
        return movie;
    }

    @Override
    public List<HistoryItem> findAll(Specification<HistoryItem> spec, Pageable pageable) {
        return this.historyItemRepository.findAll(spec,pageable).getContent();
    }

    @Override
    public History findByUser(User user) {
        return this.historyRepository.findByUser(user);
    }

    @Override
    public void delete(Movie movie) {
        this.historyItemRepository.deleteByMovie(movie);

    }
}
