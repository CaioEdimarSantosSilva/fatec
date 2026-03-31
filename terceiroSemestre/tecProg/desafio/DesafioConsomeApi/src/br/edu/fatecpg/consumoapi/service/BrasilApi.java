package br.edu.fatecpg.consumoapi.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BrasilApi {

    private static final String BASE_URL = "https://brasilapi.com.br/api/cnpj/v1/";
    public static String buscaEmpresa(String cnpj)
            throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + cnpj))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        if (status == 404) {
            throw new IllegalArgumentException(
                    "CNPJ não encontrado na base de dados da Receita Federal.");
        }
        if (status != 200) {
            throw new IOException(
                    "Erro na API BrasilAPI. Código HTTP: " + status);
        }

        return response.body();
    }
}