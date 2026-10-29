package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    @Test
    public void testCrearVeterinario() {
        Vet vet = new Vet(null, "Mario", "Lopez", "mario@example.com", "555-0101", true);

        Vet savedVet = vetService.create(vet);

        assertNotNull(savedVet.getId());
        assertEquals("Mario", savedVet.getFirstName());
        assertEquals("Lopez", savedVet.getLastName());
    }

    @Test
    public void testActualizarVeterinario() throws VetNotFoundException {
        Vet vet = new Vet(null, "Ana", "Diaz", "ana@example.com", "555-0102", true);
        Vet savedVet = vetService.create(vet);
        savedVet.setEmail("ana.diaz@example.com");
        savedVet.setPhone("555-0199");

        Vet updatedVet = vetService.update(savedVet);

        assertEquals("ana.diaz@example.com", updatedVet.getEmail());
        assertEquals("555-0199", updatedVet.getPhone());
    }

    @Test
    public void testBuscarVeterinarioPorId() throws VetNotFoundException {
        Vet vet = new Vet(null, "Luis", "Perez", "luis@example.com", "555-0103", true);
        Vet savedVet = vetService.create(vet);

        Vet foundVet = vetService.findById(savedVet.getId());

        assertEquals(savedVet.getId(), foundVet.getId());
        assertEquals("Luis", foundVet.getFirstName());
    }
}
