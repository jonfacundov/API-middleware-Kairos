package com.evalucion.tvmazemiddleware.exception;

import com.evalucion.tvmazemiddleware.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Solicitud invalida");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(mensaje));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingParameter(MissingServletRequestParameterException ex) {
        String mensaje = "Falta el parametro obligatorio: " + ex.getParameterName();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(mensaje));
    }

    @ExceptionHandler(TvMazeUnavailableException.class)
    public ResponseEntity<ErrorResponseDto> handleTvMazeUnavailable(TvMazeUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrorResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String mensaje = ex.getName() + " debe ser un valor numerico valido";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(mensaje));
    }

    @ExceptionHandler(ShowNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleShowNotFound(ShowNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(ex.getMessage()));
    }
}
