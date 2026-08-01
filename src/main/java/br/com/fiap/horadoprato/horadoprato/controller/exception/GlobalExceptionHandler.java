package br.com.fiap.horadoprato.horadoprato.controller.exception;

import br.com.fiap.horadoprato.horadoprato.dto.error.StandardError;
import br.com.fiap.horadoprato.horadoprato.dto.exception.EmailJaCadastradoException;
import br.com.fiap.horadoprato.horadoprato.dto.exception.LoginJaCadastradoException;
import br.com.fiap.horadoprato.horadoprato.dto.exception.SenhaJaCadastradaException;
import br.com.fiap.horadoprato.horadoprato.dto.exception.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EmailJaCadastradoException.class, LoginJaCadastradoException.class, SenhaJaCadastradaException.class})
    public ResponseEntity<StandardError> handleCadastroJaRealizado(EmailJaCadastradoException ex) {
        StandardError erro = new StandardError(HttpStatus.CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<StandardError> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {
        StandardError erro = new StandardError(HttpStatus.NOT_FOUND .value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND ).body(erro);
    }




}
