package com.example.demo.gutendex.repository;

import com.example.demo.gutendex.model.Authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorsRepository extends JpaRepository<Authors, Long> {
    
    @Query("SELECT a FROM Authors a WHERE (a.birthYear <= :year) AND (a.deathYear IS NULL OR a.deathYear > :year)")
    List<Authors> findLivingAuthorsInYear(int year);

    List<Authors> findByNameContainingIgnoreCase(String name);

    // Método para verificar si un autor existe por nombre
    boolean existsByName(String name);

    
}