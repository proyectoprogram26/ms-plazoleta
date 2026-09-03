package com.proyectointegrador.msplazoleta.service;

import com.proyectointegrador.msplazoleta.dto.ModificarPlatoRequest;
import com.proyectointegrador.msplazoleta.exception.AccesoDenegadoPlatoException;
import com.proyectointegrador.msplazoleta.exception.RestauranteNoEncontradoException;
import com.proyectointegrador.msplazoleta.model.Plato;
import com.proyectointegrador.msplazoleta.model.Restaurante;
import com.proyectointegrador.msplazoleta.model.Usuario;
import com.proyectointegrador.msplazoleta.repository.PlatoRepository;
import com.proyectointegrador.msplazoleta.repository.RestauranteRepository;
import com.proyectointegrador.msplazoleta.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PlatoService {

    private final PlatoRepository platoRepository;
    private final RestauranteRepository restauranteRepository;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public PlatoService(PlatoRepository platoRepository,
                        RestauranteRepository restauranteRepository,
                        RestTemplate restTemplate,
                        JwtUtil jwtUtil) {
        this.platoRepository = platoRepository;
        this.restauranteRepository = restauranteRepository;
        this.restTemplate = restTemplate;
        this.jwtUtil = jwtUtil;
    }

    public void guardarPlato(Plato plato, String token) {
        Restaurante restaurante = restauranteRepository.findById(plato.getIdRestaurante())
                .orElseThrow(() -> new RestauranteNoEncontradoException("El restaurante indicado no existe"));

        validarPropietario(restaurante.getIdPropietario(), token);

        plato.setActivo(true);
        platoRepository.save(plato);
    }

    public void modificarPlato(Long idPlato, ModificarPlatoRequest request, String token) {
        Plato plato = platoRepository.findById(idPlato)
                .orElseThrow(() -> new RestauranteNoEncontradoException("El plato indicado no existe"));

        Restaurante restaurante = restauranteRepository.findById(plato.getIdRestaurante())
                .orElseThrow(() -> new RestauranteNoEncontradoException("El restaurante indicado no existe"));

        validarPropietario(restaurante.getIdPropietario(), token);

        plato.setPrecio(request.getPrecio());
        plato.setDescripcion(request.getDescripcion());
        platoRepository.save(plato);
    }

    private void validarPropietario(Long idPropietarioRestaurante, String token) {
        String correoToken = jwtUtil.extraerCorreo(token);

        String url = "http://localhost:8080/usuarios/" + idPropietarioRestaurante;
        ResponseEntity<Usuario> respuesta = restTemplate.getForEntity(url, Usuario.class);
        Usuario propietario = respuesta.getBody();

        if (propietario == null || !propietario.getCorreo().equals(correoToken)) {
            throw new AccesoDenegadoPlatoException("Solo el propietario del restaurante puede realizar esta accion");
        }
    }
}