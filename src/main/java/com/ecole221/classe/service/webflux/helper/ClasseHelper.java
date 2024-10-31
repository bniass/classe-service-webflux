package com.ecole221.classe.service.webflux.helper;


import com.ecole221.classe.service.webflux.dto.ClasseCreateRequest;
import com.ecole221.classe.service.webflux.dto.ClasseCreateResponse;
import com.ecole221.classe.service.webflux.exception.ClasseServiceException;
import com.ecole221.classe.service.webflux.exception.ClasseServiceNotFoundException;
import com.ecole221.classe.service.webflux.exception.CustomException;
import com.ecole221.classe.service.webflux.mapper.Mapper;
import com.ecole221.classe.service.webflux.services.IClasse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClasseHelper {
    private final IClasse classeService;
    private final Mapper mapper;

    public ClasseHelper(IClasse classeService, Mapper mapper) {
        this.classeService = classeService;
        this.mapper = mapper;
    }

    public Mono<Void> checkDataClasse(ClasseCreateRequest classeCreateRequest) {
        return classeService.findByClasse(classeCreateRequest.getLibelle())
                 .flatMap(existingClasse -> Mono.error(new ClasseServiceNotFoundException(
                                "La classe [" + existingClasse.getLibelle() + "] existe déjà!")))
                 .switchIfEmpty(validateMontants(classeCreateRequest))
                 .doOnError(error -> {
                    // Log l'erreur si nécessaire
                    System.err.println("Erreur dans checkDataClasse : " + error.getMessage());
                 })
                 .then();
    }

    private Mono<Void> validateMontants(ClasseCreateRequest req) {
        List<String> errors = new ArrayList<>();
        if (req.getMensualite() <= 0) {
            errors.add("La mensualité doit être positive!");
        }
        if (req.getFraisInscription() <= 0) {
            errors.add("Les frais d'inscription doivent être positifs!");
        }
        if (req.getAutreFrais() <= 0) {
            errors.add("Les autres frais doivent être positifs!");
        }
        if(!errors.isEmpty()){
            return Mono.error(new ClasseServiceException(String.join(", ", errors)));
        }
        return Mono.empty(); // Tout est valide
    }

    public Mono<ClasseCreateResponse> save(ClasseCreateRequest classeCreateRequest) {
        return checkDataClasse(classeCreateRequest)
                .then(
                        classeService.save(mapper.classeCreateRequestToClasseEntity(classeCreateRequest))
                                .flatMap(mapper::classeEntityToClasseCreateResponse) // Utilise flatMap ici
                                .onErrorResume(error -> {
                                    // Log de l'erreur, ou toute autre gestion nécessaire
                                    System.err.println("Erreur lors de la sauvegarde : " + error.getMessage());
                                    // Retourner un Mono vide ou une valeur par défaut si une erreur se produit
                                    return Mono.error(new ClasseServiceException(error.getMessage()));
                                })
                ).onErrorResume(error -> {
                    // Log de l'erreur pour checkDataClasse
                    System.err.println("Erreur lors de la vérification des données : " + error.getMessage());
                    return Mono.error(new ClasseServiceException(error.getMessage()));
                });
    }

    public Mono<ClasseCreateResponse> update(ClasseCreateRequest classeCreateRequest, long id) {
        return classeService.findById(id)
                .switchIfEmpty(Mono.error(new ClasseServiceNotFoundException(
                        "La classe avec id : " + id + ", n'existe pas!")))
                .doOnNext(existingClasse -> {
                    existingClasse.setLibelle(classeCreateRequest.getLibelle());
                    existingClasse.setCode(classeCreateRequest.getCode());
                    existingClasse.setFiliereid(classeCreateRequest.getFiliereId());
                    existingClasse.setMensualite(classeCreateRequest.getMensualite());
                    existingClasse.setAutresFrais(classeCreateRequest.getAutreFrais());
                    existingClasse.setFraisInscription(classeCreateRequest.getFraisInscription());
                })
                .flatMap(existingClasse ->  classeService.save(existingClasse)
                        .map(mapper::classeEntityToClasseCreateResponse)
                        .flatMap(response -> response));
    }
}
