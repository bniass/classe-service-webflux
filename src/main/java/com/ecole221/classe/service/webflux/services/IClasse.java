package com.ecole221.classe.service.webflux.services;


import com.ecole221.classe.service.webflux.models.Classe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IClasse {

    Flux<Classe> findAll();

    Mono<Classe> save(Classe classe);

    Mono<Classe> findByClasse(String libelle);

    Mono<Classe> findById(long id);

    Mono<Void> remove(Classe classe);
}
