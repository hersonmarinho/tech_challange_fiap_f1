package br.com.fiap.horadoprato.horadoprato.dto;


import br.com.fiap.horadoprato.horadoprato.model.Endereco;
import br.com.fiap.horadoprato.horadoprato.model.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.model.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String login,
        TipoUsuario tipoUsuario,
        Endereco endereco,
        LocalDateTime dataUltimaAlteracao
) {
    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getTipoUsuario(),
                usuario.getEndereco(),
                usuario.getDataUltimaAlteracao()
        );
    }
}