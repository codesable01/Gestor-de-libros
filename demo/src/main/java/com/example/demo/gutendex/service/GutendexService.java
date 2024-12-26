/*
package com.example.gutendex.service;

import org.springframework.stereotype.Service;

@Service
public class GutendexService {

    private final RestTemplate restTemplate;

    public GutendexService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Book> searchBooks(String query, int page, int size) {
        String url = "https://gutendex.com/books?search=" + query + "&page=" + page + "&size=" + size;
        // Realiza la llamada a la API y convierte la respuesta en una lista de libros
        // Aquí manejarías la lógica de deserialización
        return List.of(); // Resultado ficticio
    }

    public Book getBookDetails(String bookId) {
        String url = "https://gutendex.com/books/" + bookId;
        // Lógica para obtener los detalles del libro desde la API
        return new Book(); // Resultado ficticio
    }
}
*/
