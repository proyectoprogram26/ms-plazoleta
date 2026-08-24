package com.proyectointegrador.msplazoleta.service;

import com.proyectointegrador.msplazoleta.exception.PropietarioInvalidoException;
import com.proyectointegrador.msplazoleta.model.Restaurante;
import com.proyectointegrador.msplazoleta.model.Usuario;
import com.proyectointegrador.msplazoleta.repository.RestauranteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
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

            if (usuario == null) {
                throw new PropietarioInvalidoException("El propietario indicado no existe");
            }

            if (!"PROPIETARIO".equals(usuario.getRol())) {
                throw new PropietarioInvalidoException("El usuario no tiene rol de propietario");
            }

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new PropietarioInvalidoException("El propietario indicado no existe");
            }
            throw new PropietarioInvalidoException("No se pudo validar el propietario");
        } catch (ResourceAccessException e) {
            throw new PropietarioInvalidoException("No se pudo conectar al servicio de usuarios");
        } catch (Exception e) {
            throw new PropietarioInvalidoException("No se pudo validar el propietario");
        }

        return repositorio.save(restaurante);
    }
}