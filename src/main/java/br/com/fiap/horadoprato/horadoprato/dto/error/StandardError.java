package br.com.fiap.horadoprato.horadoprato.dto.error;

import java.time.LocalDateTime;

public class StandardError{
    private int status;
    private String mensagem;
    private LocalDateTime timestamp;


    public StandardError(int status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
        this.timestamp = LocalDateTime.now();
    }
}