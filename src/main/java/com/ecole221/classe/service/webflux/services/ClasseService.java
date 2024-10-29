package com.ecole221.classe.service.webflux.services;


import com.ecole221.classe.service.webflux.models.Classe;
import com.ecole221.classe.service.webflux.repositories.ClasseRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ClasseService implements IClasse{

    private final ClasseRepository classeRepository;

    public ClasseService(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    @Override
    public Flux<Classe> findAll() {
        return classeRepository.findAll();
    }

    @Override
    public Mono<Classe> save(Classe classe) {
        return classeRepository.save(classe);
    }

    @Override
    public Mono<Classe> findByClasse(String libelle) {
        return classeRepository.findByLibelle(libelle);
    }

    @Override
    public Mono<Classe> findById(long id) {
        return classeRepository.findById(id);
    }

    @Override
    public Mono<Void> remove(Classe classe) {
        return classeRepository.delete(classe);
    }
}
