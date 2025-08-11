package com.literalura.LiterAlura.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.literalura.LiterAlura.DTO.LibrosDTO;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "libros")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonAlias("id")
    private Long id;

    @JsonAlias("title")
    private String titulo;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns =  @JoinColumn(name = "autor_id")
    )

    @JsonAlias("authors")
    private List<Autor> autores;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "libro_idiomas",
            joinColumns = @JoinColumn(name = "libro_id")
    )

    @JsonAlias("languages")
    private List<String> idiomas;

    @JsonAlias("download_count")
    private Integer descargas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }



    @Override
    public String toString() {
        String nombreAutores = (autores != null && !autores.isEmpty()) ?
                autores.stream()
                        .map(Autor::getNombre).collect(Collectors.joining(",")) : "N/A";
        String idiomaStr = (idiomas != null && idiomas.isEmpty()) ?
                String.join(",", idiomas) : "N/A";
        return """
                \n====== LIBRO ====== 
                Título: %s
                Autor(es): %s
                Idioma(s): %s
                Descargas: %s
                =====================
                """.formatted(
                        titulo,
                nombreAutores,
                idiomaStr,
                descargas != null ? descargas : 0
        );

    }
}
