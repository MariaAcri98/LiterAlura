package com.literalura.LiterAlura.DTO;

import java.util.List;

public record ResponseDTO(Integer count,
                          List<LibrosDTO> results) {}
