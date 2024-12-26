package com.example.demo.gutendex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.gutendex.model.Books;
import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {

    // Consulta para obtener los 10 libros más descargados
    @Query("SELECT b FROM Books b ORDER BY b.downloadCount DESC")
    List<Books> findTop10ByOrderByDownloadCountDesc();
    
   

    

    // Usamos una consulta para buscar libros que contengan el idioma
    public List<Books> findBooksByLanguage(String language);


    public boolean existsByTitle(String title);


}
