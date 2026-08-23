package com.proyectointegrador.msplazoleta.service;

import com.proyectointegrador.msplazoleta.model.Restaurante;
import com.proyectointegrador.msplazoleta.model.Usuario;
import com.proyectointegrador.msplazoleta.repository.RestauranteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Validated
public class RestauranteService {

    private final RestauranteRepository repositorio;
    private final RestTemplate restTemplate;

    public Restaurante crearRestaurante(@Valid Restaurante restaurante) {

        String url = "http://localhost:8080/usuarios/" + restaurante.getIdPropietario();

        try {
            ResponseEntity<Usuario> respuesta = restTemplate.getForEntity(url, Usuario.class);
            Usuario usuario = respuesta.getBody();

            if (usuario == null || !"PROPIETARIO".equals(usuario.getRol())) {
                throw new RuntimeException("El propietario no es válido o no tiene rol de PROPIETARIO");
            }

        } catch (Exception e) {
            throw new RuntimeException("No se pudo validar el propietario: " + e.getMessage());
        }

        return repositorio.save(restaurante);
    }
}