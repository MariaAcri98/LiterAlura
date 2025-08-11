package com.literalura.LiterAlura.controller;


import com.literalura.LiterAlura.DTO.LibrosDTO;
import com.literalura.LiterAlura.service.GutendexService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final GutendexService gutendexService;

    public LibroController(GutendexService gutendexService) {
        this.gutendexService = gutendexService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<LibrosDTO>> buscarPorTitulo(@RequestParam String titulo) {
        try {
            List<LibrosDTO> libros = gutendexService.buscarLibros(titulo);

            if (libros.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(libros);
            }
            return ResponseEntity.ok(libros);

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al buscar los libros: " + e.getMessage(), e
            );

        }
    }

}