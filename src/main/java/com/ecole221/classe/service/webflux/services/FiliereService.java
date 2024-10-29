package com.ecole221.classe.service.webflux.services;

import com.ecole221.classe.service.webflux.models.Filiere;
import com.ecole221.classe.service.webflux.repositories.FiliereRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FiliereService implements IFiliere{
    private final FiliereRepository filiereRepository;

    public FiliereService(FiliereRepository filiereRepository) {
        this.filiereRepository = filiereRepository;
    }

    @Override
    public Mono<Filiere> save(Filiere filiere) {
        return filiereRepository.save(filiere);
    }

    @Override
    public Mono<Filiere> findById(long id) {
        return filiereRepository.findById(id);
    }

    @Override
    public Flux<Filiere> findAll() {
        return filiereRepository.findAll();
    }

    @Override
    public void remove(long id) {
        filiereRepository.deleteById(id);
    }
}
