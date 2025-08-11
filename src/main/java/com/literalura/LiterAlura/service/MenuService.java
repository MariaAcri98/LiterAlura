package com.literalura.LiterAlura.service;

import com.literalura.LiterAlura.DTO.LibrosDTO;
import com.literalura.LiterAlura.modelo.Autor;
import com.literalura.LiterAlura.modelo.Libro;
import com.literalura.LiterAlura.repository.AutorRepository;
import com.literalura.LiterAlura.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final Scanner scanner = new Scanner(System.in);
    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;
    private final GutendexService gutendexService;

    public MenuService(AutorRepository autorRepository, LibroRepository libroRepository, GutendexService gutendexService) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
        this.gutendexService = gutendexService;
    }

    public  void mostarMenu(){
        int opcion = -1;

        while (opcion !=  0){
            System.out.println("""
                    \n=== LITERALURA ===
                    1 - Buscar libro por título
                    2 - Listar libros registrados 
                    3 - Listar autores
                    4 - Listar autores vivos en un año 
                    5 - Listar libros por idioma
                    6 - Mostrar estadísticas de libros por idiomas
                    7 - Buscar autor por nombre
                    8 - Top libros más descargados 
                    9 - General estadísticas de descargas
                    0 - Salir"""
            );

            try{
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion){
                    case 1:
                        buscarLibrosPorTitulo();
                        break;

                    case 2:
                        listarLibrosRegistrados();
                        break;

                    case 3:
                        listarAutores();
                        break;

                    case 4:
                        listarAutoresPorAno();
                        break;

                    case 5:
                        listarLibrosPorIdiomas();
                        break;

                    case 6:
                        listarEstadisticasPorIdioma();
                        break;

                    case 7:
                        buscarAutorPorNombre();
                        break;

                    case 8:
                        top10LibrosMasDescargados();
                        break;

                    case 9:
                        generarEstadisticasDescargas();
                        break;

                    case 0:
                        System.out.println("Saliendo...");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Opción no válida");
                }
            }catch (NumberFormatException e){
                System.out.println("Error: Debes ingresar un número");
            }

        }

    }

    private void buscarLibrosPorTitulo(){
        System.out.println("Ingrese el titulo del libro: ");
        String titulo = scanner.nextLine();
        //Logica para buscar el libro (usando GutendexService)
        System.out.println("Buscando: " + titulo);

        try{
            List<LibrosDTO> librosEncontrados = gutendexService.buscarLibros(titulo.toLowerCase());

            if(librosEncontrados.isEmpty()){
                System.out.println("Libros no encontrados");
            } else {
               LibrosDTO librosDTO = librosEncontrados.get(0);
               //se genera una nueva entidad de libro
                    Libro libro = new Libro();
               //Transfiere los datos de DTO a la entidad nueva
                    libro.setTitulo(librosDTO.title());
                    libro.setIdiomas(librosDTO.languages());
                    libro.setDescargas(librosDTO.download_count());

                    //LOGICA DE AUTORES//
                List<Autor> autores = librosDTO.authors().stream()
                                .map(autorDTO -> {
                                    //Buscar si el autor existe en la base de datos de manera que ignore las mayusculas y minusculas
                                    Optional<Autor> autorExistente = autorRepository.findByNombreIgnoreCase(autorDTO.name());
                                    if(autorExistente.isPresent()){
                                        return autorExistente.get();
                                    } else {
                                        //si no existe, crea y guarda
                                        Autor nuevoAutor = new Autor();
                                        nuevoAutor.setNombre(autorDTO.name());
                                        nuevoAutor.setAnoNacimiento(autorDTO.birth_year());
                                        nuevoAutor.setAnoFallecimiento(autorDTO.death_year());
                                        return autorRepository.save(nuevoAutor);
                                    }
                                })
                                        .collect(Collectors.toList());
                libro.setAutores(autores);

                    libroRepository.save(libro);
                    System.out.println("Libro encontrado y guardado: " );
                System.out.println(libro);
               }

        }
        catch (IOException | InterruptedException e){
            System.out.println("Error al buscar libros: " + e.getMessage());
        }
        catch (RuntimeException e){
            System.out.println("Error de la API: " + e.getMessage());
        }

    }
    private void listarLibrosRegistrados(){
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()){
            System.out.println("Libros no encontrados");
            return;
        }
        System.out.println("\n=== LIBROS REGISTRADOS ===");
        libros.forEach(libro -> System.out.println(libro.toString()));
    }

    private void listarAutores(){
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()){
            System.out.println("Autores no encontrados.");
            return;
        }
        System.out.println("\n=== AUTORES REGISTRADOS ===");
        autores.forEach(autor -> System.out.println(autor.toString()));
    }

    private void listarAutoresPorAno(){
        System.out.println("Ingrese el año para filtrar autores vivos: ");
        try  {
            int ano = Integer.parseInt(scanner.nextLine());

            List<Autor> autores = autorRepository.findAutoresVivosEnAno(ano);

            if (autores.isEmpty()){
                System.out.println("No hay autores vivos en " + ano);
                return;
            }
            System.out.println("\n=== AUTORES VIVOS EN " + ano + " ===");
            autores.forEach(autor -> System.out.println(autor.toString()));
        }catch (NumberFormatException e){
            System.out.println("Error: Ingrese un año válido (ej: 1850)");
        }
    }

    private void listarLibrosPorIdiomas(){
        System.out.println("""
                Idiomas disponibles: 
                es - Español 
                en - Inglés
                fr - Francés
                pt - Portugués
                Ingrese el código del idioma: """);

        String idioma = scanner.nextLine().toLowerCase();

        if (!List.of("es", "en", "fr", "pt").contains(idioma)){
            System.out.println("Error: Idioma no encontrada");
            return;
        }

        List<Libro> libros = libroRepository.findByIdiomasContaining(idioma);

        if (libros.isEmpty()){
            System.out.println("No hay libros en " + idioma);
            return;
        }

        System.out.println("\n=== LIBROSS EN " + idioma.toUpperCase() + " ===");
        libros.forEach(libro -> System.out.println(libro.toString())
        );

    }
     private void listarEstadisticasPorIdioma(){
         System.out.println("""
                 Idiomas disponibles para estadísticas:
                 es - Español 
                 en - Inglés
                 Ingrese el código del idioma: """);
         String idioma = scanner.nextLine().toLowerCase();
         if (!List.of("es", "en" ).contains(idioma)){
             System.out.println("Error: Idioma no válido para estadísticas. Elija 'es' o 'en'.");
             return;
         }

         List<Libro> libroEncontrados = libroRepository.findByIdiomasContaining(idioma);
         long cantidad = libroEncontrados.size();

         System.out.println("--------------------------------------");
         System.out.printf("Cantidadd de libros en %s: %d\n", idioma.toUpperCase(), cantidad);
         System.out.println("--------------------------------------");
     }
     //Metodo paraa buscar autor por nombre
    private void buscarAutorPorNombre(){
        System.out.println("Ingrese el nombre del autor que desea buscar: ");
        String nombre = scanner.nextLine();
        List<Autor> autoresEncontrados = autorRepository.findByNombreIgnoreCaseContaining(nombre);

        if (autoresEncontrados.isEmpty()){
            System.out.println("No se encontraron autores con ese nombre.");
        } else {
            System.out.println("\n=== AUTORES ENCONTRADOS ===");
            autoresEncontrados.forEach(autor ->
                    System.out.println(autor.toString()));
        }
    }
    //Metodo para el top de libros mas descargados
    private void top10LibrosMasDescargados(){
        List<Libro> top10Libros = libroRepository.findTop10ByOrderByDescargasDesc();

        if (top10Libros.isEmpty()){
            System.out.println("No hay libros regitrados para mostrar un top 10.");
            return;
        }

        System.out.println("\n==== TOP 10 LIBROS MÁS DESCARGADOS ====");
        top10Libros.forEach(libro -> System.out.println(libro.toString()));
    }
    //metodo para general estadisticas de descargas
    private void generarEstadisticasDescargas(){
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()){
            System.out.println("No hay libros registrados para generar estadísticas.");
            return;
        }
        DoubleSummaryStatistics stats = libros.stream()
                .mapToDouble(Libro::getDescargas)
                .summaryStatistics();
        System.out.println("\n=== ESTADÍSTICAS DE DESCARGAS ===");
        System.out.printf("Cantidad de libros: %d\n", stats.getCount());
        System.out.printf("Promedio de descargas: %.2f\n", stats.getAverage());
        System.out.printf("Descargas mínimas: %d\n",(long) stats.getMin());
        System.out.printf("Descargas máximas: %d\n", (long) stats.getMax());
        System.out.println("-------------------------------------");
    }


}
