package com.example.demo.gutendex.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookReader") // Define el nombre de la tabla
public class BookReader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // El identificador único para la entidad
    

    @JsonProperty("copyright")
    @Column(nullable = false)

    private boolean copyright;
    @JsonProperty("media_type")
    private String mediaType;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "book_reader_formats", joinColumns = @JoinColumn(name = "book_reader_id"))
    @MapKeyColumn(name = "format_key")
    @Column(name = "format_value")
    private Map<String, String> formats;

    // Constructor vacío requerido por JPA
    public BookReader() {
    }

    // Constructor parametrizado
    public BookReader(boolean copyright, String mediaType, Map<String, String> formats) {
        this.copyright = copyright;
        this.mediaType = mediaType;
        this.formats = formats;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getcopyright() {
        return copyright;
    }

    public void setCopyright(boolean copyright) {
        this.copyright = copyright;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Map<String, String> getFormats() {
        return formats;
    }

    public void setFormats(Map<String, String> formats) {
        this.formats = formats;
    }

    // Métodos adicionales
    public String getFormatLink(String format) {
        return formats.getOrDefault(format, "Formato no disponible");
    }

    public String getEpubLink() {
        return getFormatLink("application/epub+zip");
    }

    public String getMobiLink() {
        return getFormatLink("application/x-mobipocket-ebook");
    }

    public String getPlainTextLink() {
        return getFormatLink("text/plain; charset=us-ascii");
    }

    public String getHtmlLink() {
        return getFormatLink("text/html");
    }

    public String getCoverImageLink() {
        return getFormatLink("image/jpeg");
    }

    @Override
    public String toString() {
        return "BookReader{" +
                "id=" + id +
                ", copyright=" + copyright +
                ", mediaType='" + mediaType + '\'' +
                ", formats=" + formats +
                '}';
    }
}
