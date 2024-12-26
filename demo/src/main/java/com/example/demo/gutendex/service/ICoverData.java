package com.example.demo.gutendex.service;

public interface ICoverData {

    <T> T obtenerDatos(String json, Class<T>clase);
    
}
