package com.example.demo.gutendex.dto;

import java.util.List;

public record BookDto(
    int id,
    String title,
    List<String> authors, // Solo los nombres de los autores
    List<String> subjects,
    boolean copyright,
    String mediaType,
    int downloadCount
) {
    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", subjects=" + subjects +
                ", copyright=" + copyright +
                ", mediaType='" + mediaType + '\'' +
                ", downloadCount=" + downloadCount +
                '}';
    }
}
