package br.com.fiap.horadoprato.horadoprato.dto.exception;

public class LoginJaCadastradoException extends RuntimeException {
    public LoginJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}
