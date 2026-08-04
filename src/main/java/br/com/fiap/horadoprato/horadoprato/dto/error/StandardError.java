package br.com.fiap.horadoprato.horadoprato.dto.error;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandardError{
    private int status;
    private String mensagem;
    private LocalDateTime timestamp;

}