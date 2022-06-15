package com.example.cdwebbe.repository;

import com.example.cdwebbe.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findBySlug(String slug);
    boolean existsBySlug(String slug);
    Optional<Movie> findById(Long id);
}
