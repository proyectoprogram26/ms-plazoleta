package com.proyectointegrador.msplazoleta.service;

import com.proyectointegrador.msplazoleta.model.Plato;
import com.proyectointegrador.msplazoleta.repository.PlatoRepository;
import org.springframework.stereotype.Service;

@Service
public class PlatoService {

    private final PlatoRepository platoRepository;

    public PlatoService(PlatoRepository platoRepository) {
        this.platoRepository = platoRepository;
    }

    public void guardarPlato(Plato plato) {
        plato.setActivo(true);

        platoRepository.save(plato);
    }
}
