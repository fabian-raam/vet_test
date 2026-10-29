package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

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

    @Test
    public void testDeactivateVet() {

        Vet vet = new Vet(null, "Carlos", "Ramos", "carlos@example.com", "555-0104", true);

        // ------------ Create ---------------

        Vet newVet = this.vetService.create(vet);
        log.info("VET CREATED :" + newVet);

        // ------------ Deactivate ---------------

        try {
            this.vetService.deactivate(newVet.getId());
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------

        try {
            Vet foundVet = this.vetService.findById(newVet.getId());
            assertFalse(foundVet.isActive());   // sigue existiendo, pero inactivo
        } catch (VetNotFoundException e) {
            fail("El registro no debió borrarse");
        }
    }

    /**
     *
     */
    @Test
    public void testReactivateVet() {

        Vet vet = new Vet(null, "Rosa", "Mendez", "rosa@example.com", "555-0105", false);

        // ------------ Create ---------------

        Vet newVet = this.vetService.create(vet);
        log.info("VET CREATED :" + newVet);

        // ------------ Reactivate ---------------

        try {
            this.vetService.reactivate(newVet.getId());
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------

        try {
            Vet foundVet = this.vetService.findById(newVet.getId());
            assertTrue(foundVet.isActive());
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }
    }

    /**
     *
     */
    @Test
    public void testFindActiveVets() {

        int INACTIVE_VET_ID = 6;   // Sharon Jenkins (active = false en la data)

        List<Vet> vets = this.vetService.findActive();
        log.info("ACTIVE VETS :" + vets);

        assertFalse(vets.isEmpty());

        for (Vet v : vets) {
            assertTrue(v.isActive());
            assertNotEquals(INACTIVE_VET_ID, v.getId());
        }
    }

    @Test
    public void testFindVetById_NotFound() {
        assertThrows(VetNotFoundException.class, () -> vetService.findById(Integer.MAX_VALUE));
    }

    @Test
    public void testDeactivateVet_NotFound() {
        assertThrows(VetNotFoundException.class, () -> vetService.deactivate(Integer.MAX_VALUE));
    }

    @Test
    @Transactional
    public void testFindInactiveVets() {
        Vet inactiveVet = vetService.create(new Vet(null, "Carmen", "Rojas", "carmen@example.com", "555-0201", false));
        Vet activeVet = vetService.create(new Vet(null, "Pedro", "Vega", "pedro@example.com", "555-0202", true));

        List<Vet> inactiveVets = vetService.findInactiveVets();

        assertTrue(inactiveVets.stream().anyMatch(vet -> vet.getId().equals(inactiveVet.getId())));
        assertFalse(inactiveVets.stream().anyMatch(vet -> vet.getId().equals(activeVet.getId())));
        assertTrue(inactiveVets.stream().noneMatch(Vet::isActive));
    }

}
