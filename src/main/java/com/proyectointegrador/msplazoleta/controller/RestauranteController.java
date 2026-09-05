package com.proyectointegrador.msplazoleta.controller;

import com.proyectointegrador.msplazoleta.model.Restaurante;
import com.proyectointegrador.msplazoleta.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurantes")
public class RestauranteController {

    private final RestauranteService servicio;

    public RestauranteController(RestauranteService servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ResponseEntity<Restaurante> crear(@Valid @RequestBody Restaurante restaurante,
                                             @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Restaurante creado = servicio.crearRestaurante(restaurante, token);
        return ResponseEntity.status(201).body(creado);
    }
}