package com.literalura.LiterAlura.repository;

import com.literalura.LiterAlura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE a.anoNacimiento <= :ano AND (a.anoFallecimiento >= :ano OR a.anoFallecimiento IS NULL)")
    List<Autor> findAutoresVivosEnAno(Integer ano);


    Optional<Autor> findByNombreIgnoreCase(String nombre);

    //Metodo para buscar autores con nombre contenga la cadena,
    //y que no sea sensible con las mayusculas
    List<Autor> findByNombreIgnoreCaseContaining(String nombre);


}
