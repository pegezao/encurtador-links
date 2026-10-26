package com.pedromattos.encurtador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(LinkNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> naoEncontrado(LinkNaoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
    }

    @ExceptionHandler(LinkExpiradoException.class)
    public ResponseEntity<Map<String, String>> expirado(LinkExpiradoException e) {
        return ResponseEntity.status(HttpStatus.GONE).body(Map.of("erro", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> invalido(MethodArgumentNotValidException e) {
        String mensagem = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(Map.of("erro", mensagem));
    }
}
