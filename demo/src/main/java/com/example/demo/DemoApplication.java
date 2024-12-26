package com.example.demo;

import com.example.demo.gutendex.principal.Principal; 
import org.springframework.boot.CommandLineRunner; 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(Principal principal) {
        return args -> {
            // Ejecutamos el menú principal
            System.out.println("Iniciando la aplicación personalizada...");
            principal.muestraElMenu();
        };
    }
}
