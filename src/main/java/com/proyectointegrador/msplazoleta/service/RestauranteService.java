package com.proyectointegrador.msplazoleta.service;

import com.proyectointegrador.msplazoleta.model.Restaurante;
import com.proyectointegrador.msplazoleta.repository.RestauranteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class RestauranteService {

    private final RestauranteRepository repositorio;

    public Restaurante crearRestaurante(@Valid Restaurante restaurante) {
        // TODO: Descomentar cuando Carlos tenga listo el endpoint
        return repositorio.save(restaurante);
    }
}
