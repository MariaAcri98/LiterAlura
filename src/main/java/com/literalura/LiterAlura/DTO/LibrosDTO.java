package com.literalura.LiterAlura.DTO;

import java.util.List;

public record LibrosDTO(
        Long id,
        String title,
        List<AutorDTO> authors,
        List<String> languages,
        Integer download_count
) {}
