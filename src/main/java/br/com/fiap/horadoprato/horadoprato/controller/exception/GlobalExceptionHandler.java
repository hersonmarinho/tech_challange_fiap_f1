package br.com.fiap.horadoprato.horadoprato.controller.exception;

import br.com.fiap.horadoprato.horadoprato.dto.exception.CadastrodeSenhaException;
import br.com.fiap.horadoprato.horadoprato.dto.exception.CredenciaisInvalidasException;
import br.com.fiap.horadoprato.horadoprato.dto.exception.EmailJaCadastradoException;
import br.com.fiap.horadoprato.horadoprato.dto.exception.LoginJaCadastradoException;
import br.com.fiap.horadoprato.horadoprato.dto.exception.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EmailJaCadastradoException.class, CadastrodeSenhaException.class, LoginJaCadastradoException.class})
    public ProblemDetail handleCadastroJaRealizado(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Conflito de cadastro");
        problemDetail.setProperty("timestamp", java.time.LocalDateTime.now());

        return problemDetail;
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ProblemDetail handleCredenciaisInvalidas(CredenciaisInvalidasException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setTitle("Credenciais inválidas");
        problemDetail.setProperty("timestamp", java.time.LocalDateTime.now());

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                ex.getBindingResult().getFieldError() != null
                        ? ex.getBindingResult().getFieldError().getDefaultMessage()
                        : "Dados inválidos.");
        problemDetail.setTitle("Dados inválidos");
        problemDetail.setProperty("timestamp", java.time.LocalDateTime.now());

        return problemDetail;
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ProblemDetail handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Cadastro não encontrado");
        problemDetail.setProperty("timestamp", java.time.LocalDateTime.now());

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleExceptionGenerica(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setTitle("Erro nao esperado!");
        problemDetail.setProperty("timestamp", java.time.LocalDateTime.now());

        return problemDetail;
    }

}
