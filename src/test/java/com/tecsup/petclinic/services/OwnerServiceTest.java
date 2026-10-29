package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;


    @Test
    void testCreateOwner() {
        Owner owner = owner(null, "Ana", "Lopez", "Lima");
        Owner savedOwner = owner(11, "Ana", "Lopez", "Lima");
        when(ownerRepository.save(owner)).thenReturn(savedOwner);

        Owner result = ownerService.create(owner);

        assertEquals(11, result.getId());
        assertEquals("Ana", result.getFirstName());
        verify(ownerRepository).save(owner);
    }

    @Test
    void testUpdateOwner() throws OwnerNotFoundException {
        Owner existingOwner = owner(1, "George", "Franklin", "Madison");
        Owner updatedOwner = owner(1, "George", "Franklin", "Monona");
        when(ownerRepository.findById(1)).thenReturn(Optional.of(existingOwner));
        when(ownerRepository.save(updatedOwner)).thenReturn(updatedOwner);

        Owner result = ownerService.update(updatedOwner);

        assertEquals("Monona", result.getCity());
        verify(ownerRepository).save(updatedOwner);
    }

    @Test
    void testDeleteOwner() throws OwnerNotFoundException {
        Owner existingOwner = owner(1, "George", "Franklin", "Madison");
        when(ownerRepository.findById(1)).thenReturn(Optional.of(existingOwner));

        ownerService.delete(1);

        verify(ownerRepository).delete(existingOwner);
    }

    @Test
    void testFindOwnerById() throws OwnerNotFoundException {
        Owner existingOwner = owner(1, "George", "Franklin", "Madison");
        when(ownerRepository.findById(1)).thenReturn(Optional.of(existingOwner));

        Owner result = ownerService.findById(1);

        assertEquals("George", result.getFirstName());
        assertEquals("Franklin", result.getLastName());
    }

    @Test
    void testFindByLastName() {
        List<Owner> owners = List.of(owner(2, "Betty", "Davis", "Sun Prairie"));
        when(ownerRepository.findByLastName("Davis")).thenReturn(owners);

        List<Owner> result = ownerService.findByLastName("Davis");

        assertEquals(1, result.size());
        assertEquals("Betty", result.getFirst().getFirstName());
    }

    @Test
    void testFindByCity() {
        List<Owner> owners = List.of(owner(1, "George", "Franklin", "Madison"));
        when(ownerRepository.findByCity("Madison")).thenReturn(owners);

        List<Owner> result = ownerService.findByCity("Madison");

        assertEquals(1, result.size());
        assertEquals("Madison", result.getFirst().getCity());
    }

    @Test
    void testFindOwnerById_NotFound() {
        when(ownerRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class, () -> ownerService.findById(99));
    }

    @Test
    void testDeleteOwner_NotFound() {
        when(ownerRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class, () -> ownerService.delete(99));
        verify(ownerRepository, never()).delete(any(Owner.class));
    }

    @Test
    void testFindByLastName_Empty() {
        when(ownerRepository.findByLastName("NoMatch")).thenReturn(List.of());

        List<Owner> result = ownerService.findByLastName("NoMatch");

        assertTrue(result.isEmpty());
    }

    private static Owner owner(Integer id, String firstName, String lastName, String city) {
        return new Owner(id, firstName, lastName, "123 Main St", city, "5551234");
    }
}
