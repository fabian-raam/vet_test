package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import java.util.List;

public interface VetService {

    Vet create(Vet vet);

    Vet update(Vet vet) throws VetNotFoundException;

    Vet findById(Integer id) throws VetNotFoundException;

    void deactivate(Integer id) throws VetNotFoundException;

    void reactivate(Integer id) throws VetNotFoundException;

    List<Vet> findActive();
}
