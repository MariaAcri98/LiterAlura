package com.literalura.LiterAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.LiterAlura.DTO.LibrosDTO;
import com.literalura.LiterAlura.DTO.ResponseDTO;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

@Service
public class GutendexService {

    private static final String API_URL = "https://gutendex.com/books/";
    private static final String ACCEPT_HEADER = "application/json";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    private GutendexService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public List<LibrosDTO> buscarLibros(String titulo) throws IOException, InterruptedException {
        String url = API_URL + "?search=" + URLEncoder.encode(titulo, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", ACCEPT_HEADER)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 200) {
           try {
               ResponseDTO responseDTO = objectMapper.readValue(response.body(), ResponseDTO.class);
               return responseDTO.results();
           }catch (JsonProcessingException e){
               throw new RuntimeException("Error al procesar JSON: " + e.getMessage());
           }
        } else {
            throw new RuntimeException("Error en la API. Código: " + response.statusCode()
            + ". Mensaje: " + response.body());
        }
    }
}
