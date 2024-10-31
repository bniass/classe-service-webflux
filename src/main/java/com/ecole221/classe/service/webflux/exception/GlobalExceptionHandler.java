package com.ecole221.classe.service.webflux.exception;

import com.ecole221.classe.service.webflux.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    public Mono<ErrorDTO> handleException(Exception exception){
        return Mono.just(ErrorDTO.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value()+"")
                .message(exception.getMessage())
                .build());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(value = {ClasseServiceNotFoundException.class})
    public Mono<ErrorDTO> handleException(ClasseServiceNotFoundException exception){
        return Mono.just(ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.value()+"")
                .message(exception.getMessage())
                .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = {ClasseServiceException.class})
    public Mono<ErrorDTO> handleException(ClasseServiceException exception){
        return Mono.just(ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.value()+"")
                .message(exception.getMessage())
                .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Flux<Map<String, String>> handleValidationsException(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error)->{
            errors.put(((FieldError) error).getField(), error.getDefaultMessage());
        });
        return Flux.just(errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {CustomException.class})
    public Mono<ResponseEntity<List<String>>> validationExceptionHandler(CustomException exception){
        return Mono.just(new ResponseEntity<>(exception.getErrors(), HttpStatus.CONFLICT));
    }

}
