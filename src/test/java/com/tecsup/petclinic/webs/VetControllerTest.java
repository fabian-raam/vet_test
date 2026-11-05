package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.util.TObjectCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper om = new ObjectMapper();

    // --- Integrante B: Creación y soft delete ---

    @Test
    public void testCreateVet() throws Exception {
        Vet inputVet = TObjectCreator.newVet();
        String json = om.writeValueAsString(inputVet);

        MvcResult result = this.mockMvc.perform(post("/vets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andReturn();

        // Opcional: Verificar que se guardó realmente y tiene ID
        String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Vet createdVet = om.readValue(responseBody, Vet.class);
        assertThat(createdVet.getId()).isNotNull();
    }

    @Test
    public void testDeactivateVet() throws Exception {
        Integer id = 1;

        // 1. Desactivar
        this.mockMvc.perform(put("/vets/" + id + "/deactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(false));

        // 2. Verificar que al hacer GET sigue devolviendo active=false
        this.mockMvc.perform(get("/vets/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(false));
    }

    @Test
    public void testReactivateVet() throws Exception {
        Integer id = 6;

        // 1. Reactivar
        this.mockMvc.perform(put("/vets/" + id + "/reactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(true));

        // 2. Verificar persistencia
        this.mockMvc.perform(get("/vets/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(true));
    }
}