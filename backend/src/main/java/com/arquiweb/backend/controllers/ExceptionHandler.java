package com.arquiweb.backend.controllers;

import com.arquiweb.backend.controllers.dto.ErrorDTO;
import com.arquiweb.backend.models.Exception.NoExisteElItemException;
import com.arquiweb.backend.models.Exception.NoExisteElStatus;
import com.arquiweb.backend.models.Exception.NoExisteLaCarpetaException;
import com.arquiweb.backend.models.Exception.NombreVacioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorDTO> handleNoExisteLaCarpeta(NoExisteLaCarpetaException e) {
        return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorDTO> handleNombreVacio(NombreVacioException e) {
        return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorDTO> handleNoexisteElItem(NoExisteElItemException e) {
        return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorDTO> handleNoexisteStatus(NoExisteElStatus e) {
        return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage()));
    }

}
