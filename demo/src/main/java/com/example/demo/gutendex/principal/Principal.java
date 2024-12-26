package com.example.demo.gutendex.principal;

// Importación de clases del proyecto y librerías necesarias
import com.example.demo.gutendex.model.Authors; // Representa a los autores de los libros.
import com.example.demo.gutendex.model.AuthorsData;
import com.example.demo.gutendex.model.BooksData; // Contiene la estructura de datos de un libro.
import com.example.demo.gutendex.model.Categoria; // Enum que maneja las categorías de libros.
import com.example.demo.gutendex.model.BookReader; // Clase para manejar los formatos y enlaces de libros.
import com.example.demo.gutendex.model.Books;
import com.example.demo.gutendex.service.ConsumoAPI; // Clase para consumir la API externa.
import com.example.demo.gutendex.service.ConvertData; // Clase para convertir datos JSON en objetos Java.
import com.example.demo.gutendex.repository.AuthorsRepository;
import com.example.demo.gutendex.repository.BooksRepository; 

import com.fasterxml.jackson.core.type.TypeReference; // Clase para manejar genéricos en la conversión de JSON.

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Component;
@Component

public class Principal {

    // Declaración de variables y objetos principales utilizados en el programa
    private final Scanner teclado = new Scanner(System.in); // Para capturar la entrada del usuario.
    private final ConsumoAPI consumoApi = new ConsumoAPI(); // Clase que gestiona la interacción con la API.
    private final String URL_BASE = "https://gutendex.com/books/?search="; // URL base para las consultas.
    private final ConvertData conversor = new ConvertData(); // Clase que ayuda a convertir los datos obtenidos de la API.
    private final List<BooksData> librosBuscados = new ArrayList<>(); // Almacena libros completos buscados.
    private final List<Integer> librosSeleccionadosPorId = new ArrayList<>(); // Almacena los IDs de libros seleccionados.
    private final List<Authors> Authoresbuscados = new ArrayList<>();
    private final BooksRepository booksRepository; // Declaración de la variable BooksRepository
    private final AuthorsRepository authorsRepository;
     // Constructor de la clase Principal
     public Principal(BooksRepository booksRepository, AuthorsRepository authorsRepository) {
        this.booksRepository = booksRepository;
        this.authorsRepository = authorsRepository;
    }
    /**
     * Método principal que muestra el menú y gestiona las opciones del usuario.
     */
    public void muestraElMenu() {

        int opcion=-1; // Inicializa la opción con un valor que no sea válido para entrar en el ciclo
        while (opcion != 0) {
            // Menú de opciones  // Muestra las opciones disponibles al usuario.

            var menu = """
                📚 1 - Buscar libros por título
                📖 2 - Buscar libros por categoría
                🔍 3 - Ver libros buscados (temporal)
                🗂️ 4 - Mostrar todos los libros registrados (base de datos)
                ✍️ 5 - Buscar libros por autor
                👨‍🏫 6 - Listar todos los autores registrados (base de datos)
                👴 7 - Listar autores vivos en un año específico (base de datos)
                🌍 8 - Cantidad de libros en un idioma (base de datos)
                📈 9 - Top 10 libros más descargados
                ❌ 0 - Salir
                """;
                System.out.print(menu); // Muestra el menú al usuario
        System.out.print("Elige una opcion : ");
        try {
            opcion = teclado.nextInt();
            teclado.nextLine(); // Limpia el salto de línea

            // Gestiona las opciones seleccionadas por el usuario.
            switch (opcion) {
                case 1 -> buscarLibros();
                case 2 -> buscarPorCategoria();
                case 3 -> mostrarLibrosBuscados();
                case 4 -> librosRegistrados();
                case 5 -> buscarPorAutor();
                case 6 -> autoresRegistrados();
                case 7 -> autoresPorAño();
                case 8 -> listarPorIdioma();
                case 9 -> mostrarTopLibrosDescargados();

                case 0 -> {
                    System.out.println("Saliendo del programa...");
                    return; // Sale del programa
                }
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            teclado.nextLine(); // Limpia la entrada inválida
        }
    }
}
    /**
     * Método para buscar libros utilizando un término específico.
     */

     private Books getBooksData() {
        System.out.println("Escribe el nombre de la serie que deseas buscar:");
        String nombreSerie = teclado.nextLine().trim();
    
        // Validar entrada del usuario
        if (nombreSerie.isEmpty()) {
            System.out.println("El nombre de la serie no puede estar vacío.");
            return null;
        }
    
        // Construcción de la URL
        String url = URL_BASE + nombreSerie.replace(" ", "+");
        System.out.println("URL generada: " + url);
    
        try {
            // Consumir la API usando ConsumoAPI
            String json = consumoApi.obtenerDatos(url);
    
            // Mostrar el JSON recibido para depuración
            System.out.println("JSON recibido: " + json);
    
            // Convertir datos JSON a objeto BooksData
            BooksData booksData = conversor.obtenerDatos(json, BooksData.class);
            if (booksData == null) {
                System.out.println("No se encontraron resultados para la búsqueda.");
                return null;
            }
    
            // Crear y retornar un objeto Books
            return new Books(booksData);
    
        } catch (Exception e) {
            System.out.println("Error al procesar la búsqueda: " + e.getMessage());
            return null;
        }
    }
    
    
    
    
    

    private void buscarLibros() {
        System.out.println("\nEscribe el término de búsqueda del libro:");
        String terminoBusqueda = teclado.nextLine().trim().replace(" ", "+"); // Reemplaza espacios por '+' para la URL.

        buscarYProcesarLibros(URL_BASE + terminoBusqueda);
    }




    private void librosRegistrados() {
        List<Books> books = booksRepository.findAll();
        if (books.isEmpty()) {
            System.out.println("No hay libros registrados");
            return;
        }
        System.out.println("----- LOS LIBROS REGISTRADOS SON: -----\n");
        books.stream()
                .sorted(Comparator.comparing(Books::getTitle))
                .forEach(System.out::println);
    }

    private void autoresRegistrados() {
        List<Authors> authors = authorsRepository.findAll();
        if (authors.isEmpty()) {
            System.out.println("No hay autores registrados");
            return;
        }
        System.out.println("----- LOS AUTORES REGISTRADOS SON: -----\n");
        authors.stream()
                .sorted(Comparator.comparing(Authors::getName))
                .forEach(System.out::println);
    }




    private void autoresPorAño() {
        System.out.println("Escribe el año en el que deseas buscar: ");
        var año = teclado.nextInt();
        teclado.nextLine();
        if(año < 0) {
            System.out.println("El año no puede ser negativo, intenta de nuevo");
            return;
        }
        List<Authors> autoresPorAño = authorsRepository.findLivingAuthorsInYear(año);
        if (autoresPorAño.isEmpty()) {
            System.out.println("No hay autores registrados en ese año");
            return;
        }
        System.out.println("----- LOS AUTORES VIVOS REGISTRADOS EN EL AÑO " + año + " SON: -----\n");
        autoresPorAño.stream()
        .sorted(Comparator.comparing(Authors::getName))
        .forEach(autor -> System.out.println(
            autor.getName() + " (" + autor.getBirthYear() + " - " 
            + (autor.getDeathYear() == null ? "vivo" : autor.getDeathYear()) + ")"
            ));
        }



        private void listarPorIdioma() {
            System.out.println("Escribe el idioma por el que deseas buscar: ");
            String menu = """
                    es - Español
                    en - Inglés
                    fr - Francés
                    pt - Portugués
                    """;
            System.out.println(menu);
        
            // Leer y validar el idioma ingresado
            var idioma = teclado.nextLine().toLowerCase();
            List<String> idiomasValidos = List.of("es", "en", "fr", "pt");
            if (!idiomasValidos.contains(idioma)) {
                System.out.println("Idioma no válido, intenta de nuevo");
                return;
            }
        
            // Consultar libros por idioma
            List<Books> librosPorIdioma = booksRepository.findBooksByLanguage(idioma);
            if (librosPorIdioma.isEmpty()) {
                System.out.println("No hay libros registrados en ese idioma");
                return;
            }
        
            // Mostrar libros ordenados por título
            System.out.println("----- LOS LIBROS REGISTRADOS EN EL IDIOMA '" + idioma.toUpperCase() + "' SON: -----\n");
            librosPorIdioma.stream()
                    .sorted(Comparator.comparing(Books::getTitle, String.CASE_INSENSITIVE_ORDER))
                    .forEach(libro -> System.out.println(libro.getTitle() + " por " + libro.getAuthor().getName()));
        }
        
    /**
     * Método para buscar libros según una categoría.
     */
    private void buscarPorCategoria() {
        System.out.println("\nSeleccione una categoría:");
        for (Categoria categoria : Categoria.values()) { // Itera sobre las categorías disponibles.
            System.out.println("- " + categoria.getCategoriaBooks());
        }
        System.out.print("Categoría: ");
        String categoriaIngresada = teclado.nextLine();

        try {
            Categoria categoriaSeleccionada = Categoria.fromString(categoriaIngresada); // Valida la categoría ingresada.
            buscarYProcesarLibros(URL_BASE + categoriaSeleccionada.getCategoriaBooks());
        } catch (IllegalArgumentException e) {
            System.out.println("Categoría no válida. Intente nuevamente.");

        }}

        /**
        * Método para buscar libros según el Author.
        */


/**
     * Método que realiza la búsqueda de libros y los procesa.
     
     */ 

    private void buscarPorAutor() {
        System.out.println("\nEscribe el nombre del autor que deseas buscar:");
        String nombreAutor = teclado.nextLine().trim().replace(" ", "+");  // Reemplaza los espacios por '+'
    
        // Asegúrate de que la consulta sea específica para autor
        String urlBusqueda = URL_BASE + nombreAutor;
    
        buscarYProcesarLibros(urlBusqueda);
    }

   

 /**
 * Método que realiza la búsqueda de libros y los procesa.
 */
private void buscarYProcesarLibros(String url) {
    String json = consumoApi.obtenerDatos(url); // Obtiene los datos de la API en formato JSON.

    try {
        Map<String, Object> response = conversor.obtenerDatos(json, new TypeReference<>() {});

        if (response.containsKey("results")) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> rawBooks = (List<Map<String, Object>>) response.get("results");

            List<BooksData> libros = rawBooks.stream()
                    .map(bookData -> conversor.mapToObject(bookData, BooksData.class))
                    .toList();

            if (libros.isEmpty()) {
                System.out.println("No se encontraron libros.");
                return;
            }

            System.out.println("\nLibros encontrados:");
            for (BooksData libro : libros) {
                boolean regresarAlMenu = mostrarLibroUnoPorUno(libro); // Controla si se regresa al menú
                if (regresarAlMenu) {
                    return; // Rompe el bucle y regresa al menú principal
                }
            }

             // Opción para mostrar detalles por ID después de listar los libros
             mostrarDetallesDeLibro(libros);




        } else {
            System.out.println("Respuesta inesperada de la API. No se encontraron resultados.");
        }
    } catch (RuntimeException e) {
        System.out.println("Error procesando los datos: " + e.getMessage());
    }
}

private boolean mostrarLibroUnoPorUno(BooksData libro) {
    System.out.println("\nID: " + libro.id());
    System.out.println("Título: " + libro.title());
    System.out.println("Autor: " + (libro.authorsdata().isEmpty() ? "Desconocido" : libro.authorsdata().get(0).name()));
    System.out.println("Idioma: " + (libro.languages().isEmpty() ? "Desconocido" : libro.languages().get(0)));
    System.out.println("Descargas: " + libro.downloadCount());
    System.out.println("---------");
    System.out.println("\n¿Qué desea hacer?");
    System.out.println("1 - Guardar este libro");
    System.out.println("2 - Ver el siguiente libro");
    System.out.println("3 - Mostrar detalles de un libro específico");
    System.out.println("0 - Regresar al menú principal");

    int opcion;
    while (true) {
        System.out.print("Elige una opción: ");
        try {
            opcion = teclado.nextInt();
            teclado.nextLine(); // Limpia el salto de línea

            switch (opcion) {
                case 1 -> guardarLibroSiUsuarioDesea(libro);
                case 2 -> {
                    return false; // Continúa con el siguiente libro
                }
                case 3 -> {
                    mostrarDetallesDeLibro(List.of(libro)); // Llama a mostrarDetallesDeLibro con el libro actual
                    break;
                }
                case 0 -> {
                    System.out.println("Regresando al menú principal...");
                    return true; // Indica que se regrese al menú principal
                }
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            teclado.nextLine(); // Limpia la entrada inválida
        }
    }
}

private void guardarLibroSiUsuarioDesea(BooksData libroData) {
    Books libro = new Books(libroData);

    List<AuthorsData> authorsDataList = libroData.authorsdata();
    if (authorsDataList != null && !authorsDataList.isEmpty()) {
        List<Authors> authors = authorsDataList.stream()
                .map(Authors::new)
                .toList();

        libro.setAuthor(authors.get(0)); // Asigna el primer autor de la lista

        guardarLibroEnBaseDeDatos(libro);
    } else {
        System.out.println("No se encontró un autor para este libro.");
    }
}


    /**
     * Método para mostrar los detalles de un libro seleccionado.
     */
    private void mostrarDetallesDeLibro(List<BooksData> libros) {
        System.out.println("\nIngresa el ID del libro que deseas visualizar:");
        try {
            int libroId = teclado.nextInt();
            teclado.nextLine(); // Consumir el salto de línea.

            // Uso de Stream y filter para buscar el libro por su ID.
            libros.stream()
                    .filter(libro -> libro.id() == libroId) // Filtra el libro con el ID indicado.
                    .findFirst() // Busca el primer libro que cumple con el filtro.
                    .ifPresentOrElse(libro -> { // Si lo encuentra, ejecuta esta parte.
                        librosSeleccionadosPorId.add(libroId); // Agrega el ID a la lista de seleccionados.

                        // Muestra los detalles del libro seleccionado.
                        BookReader bookReader = new BookReader(
                                libro.copyrighted(),
                                libro.mediaType(),
                                libro.formats()
                        );
                        System.out.println("\nInformación del libro seleccionado:");
                        System.out.println("Título: " + libro.title());
                     //   libro.authors().forEach(author -> // Itera sobre los autores.
                    // Verifica si el libro tiene autores

                  //  List<AuthorsData> authorsDataList = libro.authorsdata();

                     if (libro.authorsdata() != null && !libro.authorsdata().isEmpty()) { 
                        // Recorre la lista de autores persistidos
                        for (AuthorsData authorsData : libro.authorsdata()) {
                            // Aquí creas un objeto Authors a partir de AuthorsData
        Authors author = new Authors(authorsData); // Asumiendo que tienes un constructor en Authors que recibe un AuthorsData

                            System.out.printf("  - %s (Nacido: %s, Fallecido: %s)%n",
                         author.getName(), // Nombre del autor desde la entidad Authors
                     author.getBirthYear() != null ? author.getBirthYear() : "Desconocido",
                       author.getDeathYear() != null ? author.getDeathYear() : "Desconocido"
                                  );
                                }
                             } else {
                             System.out.println("  - Autor desconocido.");
                 }

                        // Verifica que se muestren los valores de copyright y mediaType.
                      System.out.println("Tipo de medio: " + (bookReader.getMediaType() != null && !bookReader.getMediaType().isEmpty() ? bookReader.getMediaType() : "No disponible"));
               System.out.println("Copyright: " + (bookReader.getcopyright() ? "Sí" : "No"));


                        System.out.println("Visualizar: " + bookReader.getHtmlLink());
                        System.out.println("Descargar libro: " + bookReader.getEpubLink());
                        System.out.println("Visualizar Texto : " + bookReader.getPlainTextLink());
                        System.out.println("imagen de Portada: " + bookReader.getCoverImageLink());
                    }, () -> System.out.println("No se encontró un libro con ese ID.")); // Si no encuentra el libro.
        } catch (Exception e) {
            System.out.println("Entrada inválida. Por favor, intente nuevamente.");
            teclado.nextLine(); // Limpia la entrada inválida.
        }
    }

    /**
     * Método para mostrar los libros seleccionados previamente por ID.
     */
    private void mostrarLibrosBuscados() {
        if (librosSeleccionadosPorId.isEmpty()) {
            System.out.println("No hay libros seleccionados por ID.");
        } else {
            System.out.println("\nLibros seleccionados previamente por ID:");

            // Uso de Stream, distinct y filter para procesar la lista de libros seleccionados.
            librosSeleccionadosPorId.stream()
                .distinct() // Elimina IDs duplicados.
                .forEach(id -> librosBuscados.stream() // Para cada ID, busca el libro en la lista global.
                    .filter(libro -> libro.id().equals(id)) // Filtra por el ID.

                    .findFirst() // Obtiene el primer libro que cumple con el filtro.
                    .ifPresent(libro -> { // Si lo encuentra, muestra la información.
                        System.out.println("ID: " + libro.id());
                        System.out.println("Título: " + libro.title());
                        System.out.println("---------");
                    }));
        }
    }


    private void guardarLibroEnBaseDeDatos(Books libro) {
        // Verifica si el autor no es nulo
        if (libro.getAuthor() != null) {
            Authors author = libro.getAuthor();
            
            try {
                // Si el autor no tiene ID, significa que es nuevo
                if (author.getId() == null || !authorsRepository.existsById(author.getId())) {
                    // Verifica si el autor ya existe en la base de datos por nombre
                    if (!authorsRepository.existsByName(author.getName())) {  // Asegúrate de que tienes un método que verifique el nombre del autor
                        authorsRepository.save(author); // Guarda el autor si no existe
                        System.out.println("El autor ha sido guardado en la base de datos.");
                    } else {
                        System.out.println("El autor ya está guardado en la base de datos.");
                    }
                }
    
                // Establece la relación entre el libro y el autor
                libro.setAuthor(author);
    
                // Recorta el título si excede los 255 caracteres
                String title = libro.getTitle();
                if (title != null && title.length() > 255) {
                    title = title.substring(0, 255); // Recorta el título a 255 caracteres
                    libro.setTitle(title);
                }
    
                // Verifica si el libro ya existe en la base de datos (puedes usar el título u otro campo único)
                if (booksRepository.existsByTitle(libro.getTitle())) {
                    System.out.println("El libro ya está guardado en la base de datos.");
                } else {
                    // Guarda el libro en la base de datos
                    booksRepository.save(libro);
                    System.out.println("El libro y su autor han sido guardados en la base de datos.");
                }
    
            } catch (Exception e) {
                System.out.println("Error al guardar el libro o el autor: " + e.getMessage());
            }
        } else {
            System.out.println("El libro no tiene autor.");
        }
    }
    
    


    /**
 * Método para obtener y mostrar los 10 libros más descargados.
 */
private void mostrarTopLibrosDescargados() {
    System.out.println("\nObteniendo los 10 libros más descargados...");

    // URL de búsqueda sin filtros adicionales
    String urlBusqueda = URL_BASE;

    // Obtener datos desde la API
    String json = consumoApi.obtenerDatos(urlBusqueda);

    try {
        // Convertir los datos JSON en un mapa
        Map<String, Object> response = conversor.obtenerDatos(json, new TypeReference<>() {});

        if (response.containsKey("results")) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> rawBooks = (List<Map<String, Object>>) response.get("results");

            // Mapear los datos JSON a objetos BooksData
            List<BooksData> libros = rawBooks.stream()
                    .map(bookData -> conversor.mapToObject(bookData, BooksData.class))
                    .toList();

            // Ordenar por número de descargas y limitar a los 10 más descargados
            List<BooksData> topLibros = libros.stream()
                    .sorted((libro1, libro2) -> Integer.compare(libro2.downloadCount(), libro1.downloadCount()))
                    .limit(10)
                    .toList();

            // Mostrar los 10 libros más descargados
            if (topLibros.isEmpty()) {
                System.out.println("No se encontraron libros.");
                return;
            }

            System.out.println("\nTop 10 libros más descargados:");
            topLibros.forEach(libro -> {
                System.out.println("Título: " + libro.title());
                System.out.println("Descargas: " + libro.downloadCount());
                libro.authorsdata().forEach(author -> {
                    System.out.println("Autor: " + author.name() + ", nació en " + author.birthYear() + ", murió en " + author.deathYear());
                });
                System.out.println("Idioma: " + libro.languages());
                System.out.println("---------");
            });
        } else {
            System.out.println("Respuesta inesperada de la API. No se encontraron resultados.");
        }
    } catch (RuntimeException e) {
        System.out.println("Error procesando los datos: " + e.getMessage());
    }

}}
