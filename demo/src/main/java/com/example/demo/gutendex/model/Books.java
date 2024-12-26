package com.example.demo.gutendex.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String language;

    @ElementCollection
    private List<String> languages;


    private int downloadCount;

    // Relación obligatoria con Authors
    @ManyToOne(optional = false) // Autor es obligatorio
    @JoinColumn(name = "author_id")
    private Authors author;

    @Column(name = "name")
    private String name;


    // Relaciones opcionales
    @ManyToOne
    @JoinColumn(name = "translator_id", nullable = true) // Traductor es opcional
    private Translator translator;

    @Enumerated(EnumType.STRING)
    private Categoria category; // Opcional, solo si es útil

    @ManyToMany
    @JoinTable(
        name = "book_translator",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "translator_id")
    )
    private List<Translator> translators; // List por múltiples traductores, private para encapsulación
    
    
    
    
    //@OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
      //  private BookReader bookReader;




    @OneToOne(optional = true)
    private BookReader bookReader; // Opcional, solo si necesitas un lector relacionado

    // Constructor, Getters y Setters
    public Books() {}

    // Constructor con BooksData
    public Books(BooksData booksData) {
        this.title = booksData.title();
        this.language = booksData.languages().isEmpty() ? "Unknown" : booksData.languages().get(0); // Primer idioma
        this.downloadCount = booksData.downloadCount();
         // Mapeo de autores (suponiendo que tienes autoresData con datos completos)
    if (!booksData.authorsdata().isEmpty()) {
        // Si tienes autores, se asume que el primer autor se asigna
        // Aquí puedes mejorar si necesitas manejar más de un autor
        AuthorsData authorData = booksData.authorsdata().get(0); // Primer autor
        this.author = new Authors(authorData); // Mapea el autor desde AuthorsData a Authors
        this.name = authorData.name();  // Asigna el nombre del autor al campo authorName

    } else {
        this.author = null; // Si no hay autores, asigna null
        this.name = null;
    }
}
   



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Authors getAuthor() {
        return author;
    }

    public void setAuthor(Authors author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Books{" +
                "title='" + title + '\'' +
                ", author=" + author.getName() + // Suponiendo que Authors tiene un campo `name`
                ", language='" + language + '\'' +
                ", downloadCount=" + downloadCount +
                '}';
    }
}
