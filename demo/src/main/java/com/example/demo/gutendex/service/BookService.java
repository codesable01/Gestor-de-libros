
/*package com.example.gutendex.service;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final GutendexService gutendexService;
    private final UserRepository userRepository;

    public BookService(GutendexService gutendexService, UserRepository userRepository) {
        this.gutendexService = gutendexService;
        this.userRepository = userRepository;
    }

    // Buscar libros en Gutendex
    public List<Book> searchBooks(String query, int page, int size) {
        return gutendexService.searchBooks(query, page, size); // Delegación a GutendexService
    }

    // Obtener detalles de un libro
    public Book getBookDetails(String bookId) {
        return gutendexService.getBookDetails(bookId); // Llama a la API de Gutendex
    }

    // Agregar libro a favoritos
    public User addBookToFavorites(Long userId, Book book) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        user.getFavoriteBooks().add(book);
        return userRepository.save(user);
    }

    // Eliminar libro de favoritos
    public User removeBookFromFavorites(Long userId, String bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        user.getFavoriteBooks().removeIf(book -> book.getId().equals(bookId));
        return userRepository.save(user);
    }
}/* */
