package com.ecole221.classe.service.webflux.repositories;

import com.ecole221.classe.service.webflux.models.Filiere;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface FiliereRepository extends ReactiveCrudRepository<Filiere, Long> {
    Mono<Filiere> findByLibelle(String name);
    Flux<Filiere> findByLibelleStartingWith(String ends);
}
