package com.literalura.LiterAlura.modelo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseAPI {
    private List<Libro> results;

    public List<Libro> getResults(){
        return results;
    }
}
