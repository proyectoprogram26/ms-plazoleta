package com.proyectointegrador.msplazoleta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msplazoleta.model.Plato;
import com.proyectointegrador.msplazoleta.service.PlatoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlatoController.class)
@AutoConfigureMockMvc(addFilters = false)
class PlatoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlatoService platoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearPlato_deberiaRetornarCreatedCuandoLosDatosSonValidos() throws Exception {
        Plato plato = crearPlatoValido();
        doNothing().when(platoService).guardarPlato(any(Plato.class));

        mockMvc.perform(post("/api/platos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plato)))
                .andExpect(status().isCreated());
    }

    @Test
    void crearPlato_deberiaRetornarBadRequestCuandoElPrecioEsInvalido() throws Exception {
        Plato plato = crearPlatoValido();
        plato.setPrecio(0);

        mockMvc.perform(post("/api/platos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plato)))
                .andExpect(status().isBadRequest());
    }

    private Plato crearPlatoValido() {
        Plato plato = new Plato();
        plato.setNombre("Pizza");
        plato.setPrecio(20000);
        plato.setDescripcion("Pizza de queso y tomate");
        plato.setUrlImagen("https://ejemplo.com/pizza.jpg");
        plato.setCategoria("Comida rápida");
        return plato;
    }
}
