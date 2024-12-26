package com.example.demo.gutendex.model;



import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Data(
        @JsonAlias("results") List<BooksData> results // Cambia "BooksData" por tu clase que representa un libro
) {
}
