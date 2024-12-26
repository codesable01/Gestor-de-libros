package com.example.demo.gutendex.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

// Ignora campos desconocidos en los datos JSON
@JsonIgnoreProperties(ignoreUnknown = true)
public record BooksData(
        @JsonAlias("id") Integer id, // Corregido: solo un campo para el ID
        @JsonAlias("title") String title, // Título del libro
        @JsonAlias("download_count") int downloadCount, // Número de descargas
        @JsonAlias("copyright") boolean copyrighted, // Indicador de derechos de autor
        @JsonAlias("languages") List<String> languages, // Idiomas del libro
        @JsonAlias("translators") List<Translator> translators, // Lista de traductores
        @JsonAlias("authors") List<AuthorsData> authorsdata, // Lista de autores
        @JsonAlias("formats") Map<String, String> formats, // Formatos disponibles
        @JsonAlias("media_type") String mediaType // Tipo de medio
) {
}
