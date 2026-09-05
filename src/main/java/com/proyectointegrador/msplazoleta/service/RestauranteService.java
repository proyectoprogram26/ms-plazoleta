package com.proyectointegrador.msplazoleta.service;

import com.proyectointegrador.msplazoleta.exception.PropietarioInvalidoException;
import com.proyectointegrador.msplazoleta.model.Restaurante;
import com.proyectointegrador.msplazoleta.model.Usuario;
import com.proyectointegrador.msplazoleta.repository.RestauranteRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
@Validated
public class RestauranteService {

    private final RestauranteRepository repositorio;
    private final RestTemplate restTemplate;

    public RestauranteService(RestauranteRepository repositorio, RestTemplate restTemplate) {
        this.repositorio = repositorio;
        this.restTemplate = restTemplate;
    }

    public Restaurante crearRestaurante(@Valid Restaurante restaurante, String token) {

        String url = "http://localhost:8080/usuarios/" + restaurante.getIdPropietario();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Usuario> respuesta = restTemplate.exchange(
                    url, HttpMethod.GET, entity, Usuario.class);
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
        } catch (PropietarioInvalidoException e) {
            throw e;
        } catch (Exception e) {
            throw new PropietarioInvalidoException("No se pudo validar el propietario");
        }

        return repositorio.save(restaurante);
    }
}