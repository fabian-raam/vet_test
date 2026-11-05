package com.tecsup.petclinic.webs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VetControllerTest {

    @Autowired
    //traer una instancia ya definida e inyectarla
    private MockMvc mockMvc;
    //sirve para enviar solicitudes HTTP simuladas

    //listar
    @Test
    public void testFindAllVets() throws Exception {
        mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
                //verifica q sea un array
    }

    //buscar apellido carter
    @Test
    public void testFindVetOK() throws Exception {
        mockMvc.perform(get("/vets/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lastName").value("Carter"));
    }
    //un vet no existente
    @Test
    public void testFindVetKO() throws Exception {
        mockMvc.perform(get("/vets/666"))
                .andExpect(status().isNotFound());
    }
}
