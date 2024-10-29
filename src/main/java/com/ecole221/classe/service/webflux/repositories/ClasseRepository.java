package com.ecole221.classe.service.webflux.repositories;

import com.ecole221.classe.service.webflux.dto.ClasseData;
import com.ecole221.classe.service.webflux.models.Classe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ClasseRepository extends ReactiveCrudRepository<Classe, Long> {
    Flux<Classe> findByMensualiteBetween(int from, int to);
    Flux<Classe> findBy(Pageable pageable);
    Mono<Classe> findByLibelle(String libelle);

    @Query("""
            SELECT
              c.code,
              c.libelle libelleclasse,
              c.frais_inscription,
              c.autres_frais,
              c.mensualite,
              f.libelle Libellefiliere
              FROM
                Classe c
              INNER JOIN filiere f on c.filiereid = f.id
              ORDER BY c.mensualite
            """)
    Flux<ClasseData> getInfosClasses();
}
