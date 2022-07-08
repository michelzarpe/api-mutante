package com.michelzarpelon.mutante.config;

import com.michelzarpelon.mutante.config.exception.CalculateException;
import com.michelzarpelon.mutante.config.exception.DataIntegrityException;
import com.michelzarpelon.mutante.config.exception.ObjectWithConversionProblemsException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectWithConversionProblemsException.class)
    public ResponseEntity<?> objectWithConversionProblems(ObjectWithConversionProblemsException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<?> objectDataIntegrity(DataIntegrityException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(CalculateException.class)
    public ResponseEntity<?> calculate(CalculateException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
