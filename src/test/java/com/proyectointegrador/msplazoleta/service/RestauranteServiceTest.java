package com.proyectointegrador.msplazoleta.service;

import com.proyectointegrador.msplazoleta.exception.PropietarioInvalidoException;
import com.proyectointegrador.msplazoleta.model.Restaurante;
import com.proyectointegrador.msplazoleta.model.Usuario;
import com.proyectointegrador.msplazoleta.repository.RestauranteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteServiceTest {

    @Mock
    private RestauranteRepository repositorio;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private RestauranteService servicio;

    @Test
    void cuandoDatosSonCorrectos_debeCrearRestaurante() {
        Usuario propietario = new Usuario();
        propietario.setId(1L);
        propietario.setRol("PROPIETARIO");
        when(restTemplate.getForEntity(anyString(), eq(Usuario.class)))
                .thenReturn(ResponseEntity.ok(propietario));
        when(repositorio.save(any(Restaurante.class)))
                .thenAnswer(i -> i.getArgument(0));
        Restaurante r = new Restaurante();
        r.setNombre("Restaurante El Buen Sabor");
        r.setNit("987654321");
        r.setTelefono("+573001234567");
        r.setDireccion("Calle 123 #45-67");
        r.setIdPropietario(1L);
        Restaurante creado = servicio.crearRestaurante(r);
        assertNotNull(creado);
        assertEquals("Restaurante El Buen Sabor", creado.getNombre());
    }

    @Test
    void cuandoElRolNoEsPropietario_debeFallar() {
        Usuario noPropietario = new Usuario();
        noPropietario.setId(1L);
        noPropietario.setRol("CLIENTE");
        when(restTemplate.getForEntity(anyString(), eq(Usuario.class)))
                .thenReturn(ResponseEntity.ok(noPropietario));
        Restaurante r = new Restaurante();
        r.setNombre("Restaurante El Buen Sabor");
        r.setNit("987654321");
        r.setTelefono("+573001234567");
        r.setDireccion("Calle 123 #45-67");
        r.setIdPropietario(1L);
        PropietarioInvalidoException ex = assertThrows(PropietarioInvalidoException.class, () -> servicio.crearRestaurante(r));
        assertEquals("El usuario no tiene rol de propietario", ex.getMessage());
    }

    @Test
    void cuandoNombreEsSoloNumeros_debeFallar() {
        Restaurante r = new Restaurante();
        r.setNombre("123456789");
        r.setNit("123456789");
        r.setTelefono("+573001234567");
        r.setDireccion("Calle 123 #45-67");
        r.setIdPropietario(1L);
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> servicio.crearRestaurante(r));
    }
}