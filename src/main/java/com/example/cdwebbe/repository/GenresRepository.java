package com.example.cdwebbe.repository;

import com.example.cdwebbe.model.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenresRepository extends JpaRepository<Genres,Long> {

}
