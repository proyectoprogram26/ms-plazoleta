package com.proyectointegrador.msplazoleta.service;

import com.proyectointegrador.msplazoleta.model.Restaurante;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestauranteServiceTest {

    @Autowired
    private RestauranteService servicio;

    @Test
    void cuandoNombreEsSoloNumeros_debeFallar() {
        Restaurante r = new Restaurante();
        r.setNombre("123456789");
        r.setNit("123456789");
        r.setTelefono("+573001234567");
        r.setDireccion("Calle 123 #45-67");
        r.setIdPropietario(1L);
        assertThrows(ConstraintViolationException.class, () -> servicio.crearRestaurante(r));
    }

    @Test
    void cuandoDatosSonCorrectos_debeCrearRestaurante() {
        Restaurante r = new Restaurante();
        r.setNombre("Restaurante El Buen Sabor");
        r.setNit("987654321");
        r.setTelefono("+573001234567");
        r.setDireccion("Calle 123 #45-67");
        r.setIdPropietario(1L);
        Restaurante creado = servicio.crearRestaurante(r);
        assertNotNull(creado.getId());
    }
}