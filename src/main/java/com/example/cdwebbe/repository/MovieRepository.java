package com.example.cdwebbe.repository;

import com.example.cdwebbe.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findBySlug(String slug);
    boolean existsBySlug(String slug);
    Optional<Movie> findById(Long id);
    @Query("SELECT m FROM Movie m WHERE m.title like %:title%")
    List<Movie> search(@Param("title") String title);

    Page<Movie> findAll(Pageable pageable);
    Page<Movie> findByTitleContainingIgnoreCase(String name, Pageable pageable);
}
