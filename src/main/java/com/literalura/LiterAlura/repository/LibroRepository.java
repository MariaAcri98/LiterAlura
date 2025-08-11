package com.literalura.LiterAlura.repository;

import com.literalura.LiterAlura.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByTituloContainsIgnoreCase(String titulo);

    @Query("SELECT l FROM Libro l JOIN FETCH l.autores a WHERE :idioma MEMBER OF l.idiomas")
    List<Libro> findByIdiomasContaining(@Param("idioma") String idiomas);

    //Metodo para enccontrar los 10 mas descargados
    List<Libro> findTop10ByOrderByDescargasDesc();

}
