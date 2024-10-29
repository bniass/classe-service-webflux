package com.ecole221.classe.service.webflux.exception;

public class ClasseServiceNotFoundException extends RuntimeException{
    public ClasseServiceNotFoundException(String message) {
        super(message);
    }
}
