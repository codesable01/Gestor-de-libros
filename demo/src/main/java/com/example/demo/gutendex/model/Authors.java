package com.example.demo.gutendex.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity  // Asegúrate de que esta anotación esté presente
@Table(name = "authors")  // Puedes especificar el nombre de la tabla en la base de datos
public class Authors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autogenera el ID único
    private Long id;

    


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonAlias("name")
    private String name;

    @JsonAlias("birth_year")
    private Integer birthYear;

    @JsonAlias("death_year")
    private Integer deathYear;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Books> books;


    // Constructor vacío
    public Authors() {}

     // Constructor que recibe un AuthorsData y asigna los valores correspondientes
     public Authors(AuthorsData authorsData) {
        this.name = authorsData.name();          // Aquí accedes al valor de 'name' desde AuthorsData
        this.birthYear = authorsData.birthYear(); // Asignando birthYear desde AuthorsData
        this.deathYear = authorsData.deathYear(); // Asignando deathYear desde AuthorsData
    }




    // Getters y setters
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

    @Override
    public String toString() {
        return "Authors{" +
                "name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear +
                '}';
    }
}
