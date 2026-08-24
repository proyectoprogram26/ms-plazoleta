package com.proyectointegrador.msplazoleta.controller;

import com.proyectointegrador.msplazoleta.model.Restaurante;
import com.proyectointegrador.msplazoleta.service.RestauranteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService servicio;

    @PostMapping
    public ResponseEntity<Restaurante> crear(@Valid @RequestBody Restaurante restaurante) {
        Restaurante creado = servicio.crearRestaurante(restaurante);
        return ResponseEntity.status(201).body(creado);
    }
}
