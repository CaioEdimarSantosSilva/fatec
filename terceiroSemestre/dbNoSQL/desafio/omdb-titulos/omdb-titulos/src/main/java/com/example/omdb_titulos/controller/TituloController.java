package com.example.omdb_titulos.controller;

import com.example.omdb_titulos.model.Titulo;
import com.example.omdb_titulos.service.OmdbService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TituloController {

    private final OmdbService omdbService;

    public TituloController(OmdbService omdbService) {
        this.omdbService = omdbService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam("titulo") String titulo, Model model) {
        Titulo resultado = omdbService.buscarPorTitulo(titulo);
        model.addAttribute("titulo", resultado);
        return "resultado";
    }
}
