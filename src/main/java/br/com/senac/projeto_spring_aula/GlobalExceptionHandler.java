package br.com.senac.projeto_spring_aula;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex){

        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity
                .badRequest()
                .body(Map.of("erro", mensagem));
    }
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> handleDuplicate(DuplicateKeyException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("erro", ex.getMessage()));
    }
}
