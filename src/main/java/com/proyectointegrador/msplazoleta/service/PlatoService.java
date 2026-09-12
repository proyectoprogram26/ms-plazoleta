package com.proyectointegrador.msplazoleta.service;

import com.proyectointegrador.msplazoleta.model.Plato;
import com.proyectointegrador.msplazoleta.repository.PlatoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlatoService {

    private final PlatoRepository platoRepository;

    public PlatoService(PlatoRepository platoRepository) {
        this.platoRepository = platoRepository;
    }

    // HU-03: Crear Plato
    public Plato crearPlato(Plato plato) {
        if (plato.getNombre() == null || plato.getNombre().trim().isEmpty() || plato.getNombre().length() < 2)
            throw new IllegalArgumentException("El nombre debe tener al menos 2 caracteres");
        if (plato.getPrecio() <= 0)
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        if (plato.getDescripcion() == null || plato.getDescripcion().trim().isEmpty())
            throw new IllegalArgumentException("La descripción es obligatoria");
        
        validarNombreNoSoloNumeros(plato.getNombre());
        return platoRepository.save(plato);
    }

    // HU-04: Modificar Plato
    public Plato modificarPlato(Long idPlato, Double nuevoPrecio, String nuevaDescripcion, Long idPropietario) {
        Plato plato = platoRepository.findById(idPlato)
            .orElseThrow(() -> new IllegalArgumentException("El plato no existe"));

        // Validar que solo el propietario del restaurante pueda modificar
        if (!plato.getIdRestaurante().equals(idPropietario)) {
            throw new SecurityException("No puedes modificar platos de otro restaurante");
        }

        // Validaciones
        if (nuevoPrecio != null && nuevoPrecio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        if (nuevaDescripcion != null && nuevaDescripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }

        // Solo se permiten modificar: precio y descripción
        if (nuevoPrecio != null) plato.setPrecio(nuevoPrecio);
        if (nuevaDescripcion != null) plato.setDescripcion(nuevaDescripcion);

        return platoRepository.save(plato);
    }

    private void validarNombreNoSoloNumeros(String nombre) {
        if (nombre.matches("\\d+")) {
            throw new IllegalArgumentException("El nombre no puede ser solo números");
        }
    }

    public List<Plato> listarTodos() {
        return platoRepository.findAll();
    }
}
