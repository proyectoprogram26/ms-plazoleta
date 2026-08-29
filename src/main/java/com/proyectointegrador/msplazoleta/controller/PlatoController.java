package com.proyectointegrador.msplazoleta.controller;

import com.proyectointegrador.msplazoleta.model.Plato;
import com.proyectointegrador.msplazoleta.service.PlatoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/platos")
public class PlatoController {

    private final PlatoService platoService;

    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }

    @PostMapping
    public ResponseEntity<Void> crearPlato(@Valid @RequestBody Plato plato){
        platoService.guardarPlato(plato);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
