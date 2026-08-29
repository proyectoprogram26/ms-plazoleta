package com.proyectointegrador.msplazoleta.service;

import com.proyectointegrador.msplazoleta.model.Plato;
import com.proyectointegrador.msplazoleta.repository.PlatoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PlatoServiceTest {

    @Mock
    private PlatoRepository platoRepository;

    @InjectMocks
    private PlatoService platoService;

    @Test
    void guardarPlato_debeActivarYGuardarElPlato() {
        Plato plato = new Plato();
        plato.setNombre("Hamburguesa clásica");
        plato.setPrecio(18000);
        plato.setActivo(false);

        platoService.guardarPlato(plato);

        ArgumentCaptor<Plato> platoCaptor = ArgumentCaptor.forClass(Plato.class);
        verify(platoRepository).save(platoCaptor.capture());
        assertTrue(platoCaptor.getValue().getActivo());
        assertEquals("Hamburguesa clásica", platoCaptor.getValue().getNombre());
        assertEquals(18000, platoCaptor.getValue().getPrecio());
    }
}
