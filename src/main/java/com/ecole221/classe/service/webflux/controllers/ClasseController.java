package com.ecole221.classe.service.webflux.controllers;


import com.ecole221.classe.service.webflux.dto.ClasseCreateRequest;
import com.ecole221.classe.service.webflux.dto.ClasseCreateResponse;
import com.ecole221.classe.service.webflux.exception.ClasseServiceException;
import com.ecole221.classe.service.webflux.exception.ClasseServiceNotFoundException;
import com.ecole221.classe.service.webflux.helper.ClasseHelper;
import com.ecole221.classe.service.webflux.mapper.Mapper;
import com.ecole221.classe.service.webflux.services.IClasse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/classes")
public class ClasseController {

    private final Mapper mapper;
    private final ClasseHelper classeHelper;
    private final IClasse classeService;

    public ClasseController(Mapper mapper, ClasseHelper classeHelper, IClasse classeService) {
        this.mapper = mapper;
        this.classeHelper = classeHelper;
        this.classeService = classeService;
    }

    @GetMapping
    public Flux<ClasseCreateResponse> index() {
        return classeService.findAll()
                .flatMap(mapper::classeEntityToClasseCreateResponse);

    }

    @PostMapping
    public Mono<ClasseCreateResponse> save(@Valid @RequestBody ClasseCreateRequest classeCreateRequest) {
        return classeHelper.checkDataClasse(classeCreateRequest)
                .then(classeService.save(mapper.classeCreateRequestToClasseEntity(classeCreateRequest)))
                .map(mapper::classeEntityToClasseCreateResponse)
                .onErrorMap(ClasseServiceException.class, e ->
                        new ClasseServiceException(e.getMessage()))
                .flatMap(response -> response);
    }

    @GetMapping("/{id}")
    public Mono<ClasseCreateResponse> findById(@PathVariable long id) {
        return classeService.findById(id)
                .switchIfEmpty(Mono.error(new ClasseServiceNotFoundException(
                        "La classe avec l'id " + id + " n'existe pas!")))
                .map(mapper::classeEntityToClasseCreateResponse)
                .flatMap(response->response);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> remove(@PathVariable long id) {
        return classeService.findById(id)
                .switchIfEmpty(Mono.error(new ClasseServiceNotFoundException(
                        "La classe avec l'id " + id + " n'existe pas!")))
                .flatMap(classe -> classeService.remove(classe)
                        .thenReturn(ResponseEntity.ok("Deleted!")));
    }

    @PutMapping
    public Mono<ClasseCreateResponse> update(@Valid @RequestBody ClasseCreateRequest classeCreateRequest) {
        return classeService.findByClasse(classeCreateRequest.getLibelle())
                .switchIfEmpty(Mono.error(new ClasseServiceNotFoundException(
                        "La classe " + classeCreateRequest.getLibelle() + " n'existe pas!")))
                .flatMap(existingClasse -> classeService.save(mapper.classeCreateRequestToClasseEntity(classeCreateRequest)))
                .map(mapper::classeEntityToClasseCreateResponse)
                .flatMap(response -> response);
    }
}