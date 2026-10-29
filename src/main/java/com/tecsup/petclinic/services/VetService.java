package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;

public interface VetService {

    Vet create(Vet vet);

    Vet update(Vet vet) throws VetNotFoundException;

    Vet findById(Integer id) throws VetNotFoundException;
}
