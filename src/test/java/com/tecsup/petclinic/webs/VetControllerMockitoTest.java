package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.services.VetService;
import com.tecsup.petclinic.util.TObjectCreator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VetControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VetService vetService;

    @Test
    public void testFindActiveVets() throws Exception {
        Mockito.when(vetService.findActive()).thenReturn(TObjectCreator.getActiveVets());

        mockMvc.perform(get("/vets").param("active", "true"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[*].active", everyItem(is(true))));

        Mockito.verify(vetService).findActive();
    }

    @Test
    public void testFindInactiveVets() throws Exception {
        Mockito.when(vetService.findInactiveVets()).thenReturn(TObjectCreator.getInactiveVets());

        mockMvc.perform(get("/vets").param("active", "false"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(6)))
                .andExpect(jsonPath("$[0].active", is(false)));

        Mockito.verify(vetService).findInactiveVets();
    }

    @Test
    public void testDeleteVetKO() throws Exception {
        Mockito.doThrow(new VetNotFoundException("Vet not found: 1000"))
                .when(vetService).delete(1000);

        mockMvc.perform(delete("/vets/1000"))
                .andExpect(status().isNotFound());

        Mockito.verify(vetService).delete(1000);
    }
}
