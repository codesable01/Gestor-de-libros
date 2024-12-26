package com.example.demo.gutendex.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AuthorsData(
    @JsonAlias("name") String name,
    @JsonAlias("birth_year") Integer birthYear,
    @JsonAlias("death_year") Integer deathYear
) {}
