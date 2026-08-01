package br.com.fiap.horadoprato.horadoprato.dto.exception;

public class SenhaJaCadastradaException extends RuntimeException {
    public SenhaJaCadastradaException(String mensagem) {
        super(mensagem);
    }
}
