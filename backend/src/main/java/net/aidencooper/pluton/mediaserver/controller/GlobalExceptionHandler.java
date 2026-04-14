package net.aidencooper.pluton.mediaserver.controller;

import net.aidencooper.pluton.mediaserver.domain.dto.ErrorDTO;
import net.aidencooper.pluton.mediaserver.exception.LibraryNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Validation failed");

        ErrorDTO errorDTO = new ErrorDTO(message);
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LibraryNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleLibraryNotFoundException(LibraryNotFoundException exception) {
        String message = "Library with ID '" + exception.getId() + "' not found";
        ErrorDTO errorDTO = new ErrorDTO(message);
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
