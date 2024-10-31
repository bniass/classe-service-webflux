package com.ecole221.classe.service.webflux.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class CustomException extends RuntimeException{
    private final List<String> errors;
    public CustomException(List<String> errors) {
        this.errors = errors;
    }

}
