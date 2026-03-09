package com.usuario.quero_ler.exceptions;
import com.usuario.quero_ler.exceptions.especies.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailNaoCadastradoException.class)
    public ResponseEntity<Object> handlerEmailNaoCadastradoException(EmailNaoCadastradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<Object> handlerUsuarioNaoEncontradoException(UsuarioNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NotificacaoNaoEncontradaException.class)
    public ResponseEntity<Object> handlerNotificacaoNaoEncontradaException(NotificacaoNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DocumentoNaoEncontradoException.class)
    public ResponseEntity<Object> handlerDocumentoNaoEncontradoException(DocumentoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<Object> handlerSenhaInvalidaException(SenhaInvalidaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UsuarioNaoAutenticadoException.class)
    public ResponseEntity<Object> handlerUsuarioNaoAutenticadoException(UsuarioNaoAutenticadoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EmailInvalidoException.class)
    public ResponseEntity<Object> handlerEmailInvalidoException(EmailInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<Object> handlerEmailJaCadastradoException(EmailJaCadastradoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                erros.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleJsonError(
            HttpMessageNotReadableException ex) {

        Map<String, String> erro = new HashMap<>();

        Throwable causa = ex.getCause();

        if (causa instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException invalidFormat) {

            if (invalidFormat.getTargetType().equals(java.time.LocalDate.class)) {
                erro.put("dataDeNascimento", "Formato inválido. Use dd/MM/yyyy");
                return ResponseEntity.badRequest().body(erro);
            }
        }

        erro.put("erro", "JSON inválido.");
        return ResponseEntity.badRequest().body(erro);
    }
}