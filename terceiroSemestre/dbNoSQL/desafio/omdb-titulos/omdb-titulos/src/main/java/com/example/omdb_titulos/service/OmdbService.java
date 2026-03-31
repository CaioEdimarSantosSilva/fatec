package com.example.omdb_titulos.service;

import com.example.omdb_titulos.model.Titulo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OmdbService {

    @Value("${omdb.api.url}")
    private String apiUrl;

    @Value("${omdb.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Titulo buscarPorTitulo(String titulo) {
        String url = apiUrl + "?t=" + titulo + "&apikey=" + apiKey;
        try {
            String json = restTemplate.getForObject(url, String.class);
            return objectMapper.readValue(json, Titulo.class);
        } catch (Exception e) {
            Titulo erro = new Titulo();
            erro.setResponse("False");
            erro.setError("Erro ao consultar a API: " + e.getMessage());
            return erro;
        }
    }
}
