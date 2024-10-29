package com.ecole221.classe.service.webflux.helper;


import com.ecole221.classe.service.webflux.dto.ClasseCreateRequest;
import com.ecole221.classe.service.webflux.exception.ClasseServiceException;
import com.ecole221.classe.service.webflux.services.IClasse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ClasseHelper {
    private final IClasse classeService;

    public ClasseHelper(IClasse classeService) {
        this.classeService = classeService;
    }

    public Mono<Void> checkDataClasse(ClasseCreateRequest classeCreateRequest) {
        return classeService
                .findByClasse(classeCreateRequest.getLibelle())
                .flatMap(existingClasse -> Mono.error(new ClasseServiceException(
                        "La classe [" + existingClasse.getLibelle() + "] existe déjà!")))
                .switchIfEmpty(Mono.defer(() -> validateMontants(classeCreateRequest)))
                .then();
    }

    private Mono<Void> validateMontants(ClasseCreateRequest req) {
        if (req.getMensualite() <= 0) {
            return Mono.error(new ClasseServiceException("La mensualité doit être positive!"));
        }
        if (req.getFraisInscription() <= 0) {
            return Mono.error(new ClasseServiceException("Les frais d'inscription doivent être positifs!"));
        }
        if (req.getAutreFrais() <= 0) {
            return Mono.error(new ClasseServiceException("Les autres frais doivent être positifs!"));
        }
        return Mono.empty(); // Tout est valide
    }
}
