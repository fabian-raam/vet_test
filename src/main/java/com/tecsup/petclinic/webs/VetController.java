package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.services.VetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vets")
@RequiredArgsConstructor
public class VetController {

    private final VetService vetService;

    @GetMapping
    public List<Vet> findVets(@RequestParam(required = false) Boolean active) {
        if (active == null) {
            return vetService.findAll();
        }
        return active ? vetService.findActive() : vetService.findInactiveVets();
    }

    @GetMapping("/{id}")
    public Vet findVetById(@PathVariable Integer id) throws VetNotFoundException {
        return vetService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Vet> createVet(@RequestBody Vet vet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vetService.create(vet));
    }

    @PutMapping("/{id}/deactivate")
    public Vet deactivateVet(@PathVariable Integer id) throws VetNotFoundException {
        vetService.deactivate(id);
        return vetService.findById(id);
    }

    @PutMapping("/{id}/reactivate")
    public Vet reactivateVet(@PathVariable Integer id) throws VetNotFoundException {
        vetService.reactivate(id);
        return vetService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVet(@PathVariable Integer id) throws VetNotFoundException {
        vetService.delete(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(VetNotFoundException.class)
    public ResponseEntity<Void> handleVetNotFound(VetNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
}
