package com.example.demo.gutendex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ConvertData implements ICoverData {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir el JSON al tipo " + clase.getSimpleName() + ": " + e.getMessage(), e);
        }
    }

    // Método sobrecargado para manejar listas o estructuras genéricas
    public <T> T obtenerDatos(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir el JSON a la estructura genérica: " + e.getMessage(), e);
        }
    }

    // Método para convertir un mapa a un objeto
    public <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        try {
            return objectMapper.convertValue(map, clazz);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error al convertir el mapa al tipo " + clazz.getSimpleName() + ": " + e.getMessage(), e);
        }
    }
}
