package com.ecole221.classe.service.webflux.mapper;


import com.ecole221.classe.service.webflux.dto.ClasseCreateRequest;
import com.ecole221.classe.service.webflux.dto.ClasseCreateResponse;
import com.ecole221.classe.service.webflux.dto.FiliereCreateResponse;
import com.ecole221.classe.service.webflux.exception.ClasseServiceException;
import com.ecole221.classe.service.webflux.models.Classe;
import com.ecole221.classe.service.webflux.models.Filiere;
import com.ecole221.classe.service.webflux.services.IFiliere;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
public class Mapper {
    private final IFiliere iFiliere;
    private final ModelMapper modelMapper;

    public Mapper(IFiliere iFiliere, ModelMapper modelMapper) {
        this.iFiliere = iFiliere;
        this.modelMapper = modelMapper;
    }

    public Classe classeCreateRequestToClasseEntity(ClasseCreateRequest classeCreateRequest) {
        return Classe.builder()
                .code(classeCreateRequest.getCode())
                .libelle(classeCreateRequest.getLibelle())
                .fraisInscription(classeCreateRequest.getFraisInscription())
                .mensualite(classeCreateRequest.getMensualite())
                .autresFrais(classeCreateRequest.getAutreFrais())
                .filiereid(classeCreateRequest.getFiliereId())
                .build();
    }

    public Mono<ClasseCreateResponse> classeEntityToClasseCreateResponse(Classe classe) {
        // Appeler iFiliere de manière réactive pour récupérer la filière associée
        return iFiliere.findById(classe.getFiliereid())
                .doOnNext(filiere -> log.info("{}", filiere))
                .flatMap(filiere -> Mono.just(classeCreateResponse(classe, filiere)))
                .switchIfEmpty(Mono.error(new ClasseServiceException("Filière introuvable avec l'id: " + classe.getFiliereid())));
    }

    private ClasseCreateResponse classeCreateResponse(Classe classe, Filiere filiere) {
        return ClasseCreateResponse.builder()
                .id(classe.getId())
                .code(classe.getCode())
                .libelle(classe.getLibelle())
                .fraisInscription(classe.getFraisInscription())
                .mensualite(classe.getMensualite())
                .autreFrais(classe.getAutresFrais())
                .filiere(filiereEntityToFiliereCreateResponse(filiere))
                .build();
    }

    public FiliereCreateResponse filiereEntityToFiliereCreateResponse(Filiere filiere) {
        return FiliereCreateResponse.builder()
                .id(filiere.getId())
                .code(filiere.getCode())
                .libelle(filiere.getLibelle())
                .build();
    }
}