package br.com.fiap.horadoprato.horadoprato.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipoUsuario;

    @Embedded
    private Endereco endereco;

    @Column(name = "data_ultima_alteracao")
    private LocalDate dataUltimaAlteracao;

    @PrePersist
    @PreUpdate
    public void atualizarDataAlteracao() {
        this.dataUltimaAlteracao = LocalDate.now();
    }
}
