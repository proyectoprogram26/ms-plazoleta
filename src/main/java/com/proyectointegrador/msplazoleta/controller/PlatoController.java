package com.proyectointegrador.msplazoleta.controller;

import com.proyectointegrador.msplazoleta.model.Plato;
import com.proyectointegrador.msplazoleta.service.PlatoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/platos")
public class PlatoController {

    private final PlatoService platoService;

    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }

    @PostMapping
    public ResponseEntity<Void> crearPlato(@Valid @RequestBody Plato plato,
                                           @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        platoService.guardarPlato(plato, token);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}