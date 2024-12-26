package com.example.demo.gutendex.model;

import jakarta.persistence.*;
import java.util.List;

import java.util.stream.Collectors;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos desconocidos en la respuesta JSON



@Entity
@Table(name = "translators")
public class Translator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "birth_year", nullable = true)
    private Integer birthYear;

    @Column(name = "death_year", nullable = true)
    private Integer deathYear;

    @ManyToMany(mappedBy = "translators")
    private List<Books> books;

    public Translator() {}

    public Translator(String name, Integer birthYear, Integer deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }


    @Override
    public String toString() {
        // Verificar si el traductor tiene libros traducidos
        String librosDetalles = books != null && !books.isEmpty()
                ? books.stream()
                       .map(book -> "Título: " + book.getTitle())  // Aquí solo mostramos el título del libro
                       .collect(Collectors.joining("\n"))  // Convertir los títulos a un string separados por saltos de línea
                : "Sin libros traducidos";  // Si no tiene libros traducidos
    
        return "--------------- TRADUCTOR 👨‍🏫 ---------------" + "\n" +
                "Nombre: " + name + "\n" +
                "Fecha de nacimiento: " + (birthYear != null ? birthYear : "Desconocida") + "\n" +
                "Fecha de fallecimiento: " + (deathYear != null ? deathYear : "Desconocida") + "\n" +
                "Libros traducidos: " + "\n" + librosDetalles + "\n";  // Mostrar los libros traducidos
    }
}