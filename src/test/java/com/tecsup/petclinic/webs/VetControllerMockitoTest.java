package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.services.VetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VetControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VetService vetService;

    private final ObjectMapper om = new ObjectMapper();

    // --- Integrante B: Creación y soft delete ---

    @Test
    public void testCreateVet() throws Exception {
        // 1. Crear el objeto Vet para el JSON de entrada
        Vet inputVet = new Vet();
        inputVet.setFirstName("John");
        inputVet.setLastName("Doe");
        inputVet.setEmail("john.doe@test.com");
        inputVet.setActive(true);

        String json = om.writeValueAsString(inputVet);

        // 2. Mockear el servicio para que devuelva un Vet con ID 1000
        Vet vetReturned = new Vet(1000, "John", "Doe", "john.doe@test.com", null, true);
        when(vetService.create(any(Vet.class))).thenReturn(vetReturned);

        // 3. Ejecutar y Validar
        this.mockMvc.perform(post("/vets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1000))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void testDeactivateVet() throws Exception {
        Integer id = 1;

        // 2. Crear el Vet desactivado manualmente (según constructor: id, firstName, lastName...)
        Vet vetDesactivado = new Vet(1, "Janet", "Carter", "j.carter@test.com", "555", false);

        doNothing().when(vetService).deactivate(id);
        when(vetService.findById(id)).thenReturn(vetDesactivado);

        this.mockMvc.perform(put("/vets/" + id + "/deactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(false));
    }

    @Test
    public void testReactivateVet() throws Exception {
        Integer id = 6;

        // 3. Crear el Vet reactivado manualmente
        Vet vetReactivado = new Vet(6, "Test", "Reactive", "react@test.com", "555", true);

        doNothing().when(vetService).reactivate(id);
        when(vetService.findById(id)).thenReturn(vetReactivado);

        this.mockMvc.perform(put("/vets/" + id + "/reactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(true));
    }
}