package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;

    @Override
    public Vet create(Vet vet) {
        return vetRepository.save(vet);
    }

    @Override
    public Vet update(Vet vet) throws VetNotFoundException {
        findById(vet.getId());
        return vetRepository.save(vet);
    }

    @Override
    public Vet findById(Integer id) throws VetNotFoundException {
        return vetRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException("Vet not found: " + id));
    }

    @Override
    public void deactivate(Integer id) throws VetNotFoundException {
        Vet vet = findById(id);
        vet.setActive(false);
        vetRepository.save(vet);
    }

    @Override
    public void reactivate(Integer id) throws VetNotFoundException {
        Vet vet = findById(id);
        vet.setActive(true);
        vetRepository.save(vet);
    }

    @Override
    public List<Vet> findActive() {
        return vetRepository.findByActiveTrue();
    }


}
