package es.cic.curso25.proy009.configuration;

// src/main/java/es/cic/curso25/proy009/exception/MyControllerAdvice.java

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.cic.curso25.proy009.exception.NotFoundException;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> manejarNotFound(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Puedes añadir más excepciones si quieres
}
