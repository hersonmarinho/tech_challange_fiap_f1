package br.com.fiap.horadoprato.horadoprato.entities;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Usuario {

    private String nome;
    private String email;
    private String login;
    private String senha;
    private String tipoUsuario;
    private LocalDateTime dataUltimaAlteracao;
    private String endereco;


}
