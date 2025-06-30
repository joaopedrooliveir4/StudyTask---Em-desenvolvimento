package com.joaopedro.studytask.exception.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.joaopedro.studytask.exception.HorarioOcupadoException;
import com.joaopedro.studytask.exception.LegalArgumentException;
import com.joaopedro.studytask.exception.modelException.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handlerValidationErrors(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                errors
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleEnumError(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException ife && ife.getTargetType().isEnum()) {
            String campo = ife.getPath().get(0).getFieldName();
            String tipo = ife.getTargetType().getSimpleName();
            String[] valores = Arrays.stream(ife.getTargetType().getEnumConstants())
                    .map(Object::toString)
                    .toArray(String[]::new);

            String msg = String.format("O campo '%s' deve ser um dos valores: %s", campo, String.join(", ", valores));
            return ResponseEntity.badRequest().body(Map.of("erro", msg));
        }

        return ResponseEntity.badRequest().body(Map.of("erro", "Requisição mal formatada."));
    }

    @ExceptionHandler(HorarioOcupadoException.class)
    public ResponseEntity<String> handlerHorarioOcupado(HorarioOcupadoException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(LegalArgumentException.class)
    public ResponseEntity<ApiError> handlerLegalArgumentException(LegalArgumentException exception) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de horário definido",
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
