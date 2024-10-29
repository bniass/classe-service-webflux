package com.ecole221.classe.service.webflux.services;

import com.ecole221.classe.service.webflux.models.Filiere;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFiliere {
    Mono<Filiere> save(Filiere filiere);
    Mono<Filiere> findById(long id);
    Flux<Filiere> findAll();
    void  remove(long id);
}
