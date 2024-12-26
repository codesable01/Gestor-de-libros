package com.example.demo.gutendex.model;

public enum Categoria {
    ACCION("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIMEN("Crime"),
    TERROR("Horror"),
    FANTASIA("Fantasy"),
    CIENCIA_FICCION("Science fiction"),
    HISTORIA("History"),
    BIOGRAFIA("Biography"),
    AVENTURA("Adventure"),
    POESIA("Poetry"),
    RELIGION("Religion"),
    FILOSOFIA("Philosophy"),
    PSICOLOGIA("Psychology"),
    INFANTIL("Children's literature"),
    CIENCIA("Science"),
    MATEMATICA("Mathematics"),
    ARTE("Art"),
    MUSICA("Music"),
    LENGUA("Language"),
    TECNOLOGIA("Technology"),
    MEDICINA("Medicine"),
    GASTRONOMIA("Gastronomy"),
    NEGOCIOS("Business"),
    POLITICA("Politics");

    private final String categoriaBooks;

    Categoria(String categoriaBooks) {
        this.categoriaBooks = categoriaBooks;
    }

    /**
     * Mapea el texto proporcionado a una categoría existente.
     *
     * @param text texto a buscar.
     * @return categoría mapeada.
     */
    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaBooks.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoría encontrada: " + text);
    }

    /**
     * Devuelve el alias asociado a la categoría.
     *
     * @return alias.
     */
    public String getCategoriaBooks() {
        return categoriaBooks;
    }
}
