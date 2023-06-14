package com.krieger.warehouse.exception;

import com.krieger.warehouse.models.ServiceResponse;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestControllerAdvice
public class CustomExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<ServiceResponse<?>> handelException(ObjectNotValidException exception) {
        logger.error(String.join(";", exception.getExceptionMessages()));
        return ResponseEntity.badRequest().body(
                new ServiceResponse(null
                        , false
                        , String.join(";", exception.getExceptionMessages())));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ServiceResponse<?>> handelException(EntityNotFoundException exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ServiceResponse<?>> handelException(SQLException exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.internalServerError().body(
                new ServiceResponse(null
                        , false
                        , exception.getLocalizedMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServiceResponse<?>> handelException(Exception exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.internalServerError().body(
                new ServiceResponse(null
                        , false
                        , exception.getLocalizedMessage()));
    }
}
