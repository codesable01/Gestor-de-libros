package com.example.demo.gutendex.model;

import java.util.List;

public class ListsBooks {

    private int count;  // Total de libros encontrados
    private String next;  // URL de la siguiente página de resultados
    private String previous;  // URL de la página anterior de resultados
    private List<BooksData> results;  // Lista de libros encontrados

    // Constructor
    public ListsBooks(int count, String next, String previous, List<BooksData> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    // Getters y Setters
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<BooksData> getResults() {
        return results;
    }

    public void setResults(List<BooksData> results) {
        this.results = results;
    }

    // Método toString() para mostrar la información de la lista de libros
    @Override
    public String toString() {
        return "ListsBooks{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results +
                '}';
    }

}
