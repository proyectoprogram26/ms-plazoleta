package com.proyectointegrador.msplazoleta.service;

import com.proyectointegrador.msplazoleta.exception.AccesoDenegadoPlatoException;
import com.proyectointegrador.msplazoleta.exception.RestauranteNoEncontradoException;
import com.proyectointegrador.msplazoleta.model.Plato;
import com.proyectointegrador.msplazoleta.model.Restaurante;
import com.proyectointegrador.msplazoleta.model.Usuario;
import com.proyectointegrador.msplazoleta.repository.PlatoRepository;
import com.proyectointegrador.msplazoleta.repository.RestauranteRepository;
import com.proyectointegrador.msplazoleta.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlatoServiceTest {

    @Mock
    private PlatoRepository platoRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private PlatoService platoService;

    private Restaurante crearRestauranteDePrueba() {
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setIdPropietario(10L);
        return restaurante;
    }

    private Plato crearPlatoDePrueba() {
        Plato plato = new Plato();
        plato.setNombre("Hamburguesa clásica");
        plato.setPrecio(18000);
        plato.setDescripcion("Con queso y tocineta");
        plato.setUrlImagen("http://ejemplo.com/img.jpg");
        plato.setCategoria("Comida rápida");
        plato.setActivo(false);
        plato.setIdRestaurante(1L);
        return plato;
    }

    @Test
    void guardarPlato_debeActivarYGuardarElPlato_cuandoElPropietarioEsValido() {
        Plato plato = crearPlatoDePrueba();
        Restaurante restaurante = crearRestauranteDePrueba();

        Usuario propietario = new Usuario();
        propietario.setCorreo("dueno@correo.com");
        propietario.setRol("PROPIETARIO");

        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));
        when(jwtUtil.extraerCorreo("token-valido")).thenReturn("dueno@correo.com");
        when(restTemplate.getForEntity(anyString(), eq(Usuario.class)))
                .thenReturn(ResponseEntity.ok(propietario));

        platoService.guardarPlato(plato, "token-valido");

        ArgumentCaptor<Plato> platoCaptor = ArgumentCaptor.forClass(Plato.class);
        verify(platoRepository).save(platoCaptor.capture());
        assertTrue(platoCaptor.getValue().getActivo());
        assertEquals("Hamburguesa clásica", platoCaptor.getValue().getNombre());
    }

    @Test
    void guardarPlato_debeLanzarExcepcion_cuandoElUsuarioNoEsElPropietario() {
        Plato plato = crearPlatoDePrueba();
        Restaurante restaurante = crearRestauranteDePrueba();

        Usuario propietario = new Usuario();
        propietario.setCorreo("dueno@correo.com");
        propietario.setRol("PROPIETARIO");

        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));
        when(jwtUtil.extraerCorreo("token-de-otro-usuario")).thenReturn("otro@correo.com");
        when(restTemplate.getForEntity(anyString(), eq(Usuario.class)))
                .thenReturn(ResponseEntity.ok(propietario));

        assertThrows(AccesoDenegadoPlatoException.class,
                () -> platoService.guardarPlato(plato, "token-de-otro-usuario"));

        verify(platoRepository, never()).save(any(Plato.class));
    }

    @Test
    void guardarPlato_debeLanzarExcepcion_cuandoElRestauranteNoExiste() {
        Plato plato = crearPlatoDePrueba();

        when(restauranteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RestauranteNoEncontradoException.class,
                () -> platoService.guardarPlato(plato, "cualquier-token"));

        verify(platoRepository, never()).save(any(Plato.class));
    }
}