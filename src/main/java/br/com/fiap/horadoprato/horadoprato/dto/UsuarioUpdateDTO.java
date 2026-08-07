package br.com.fiap.horadoprato.horadoprato.dto;

import br.com.fiap.horadoprato.horadoprato.model.Endereco;
import br.com.fiap.horadoprato.horadoprato.model.TipoUsuario;
import jakarta.annotation.Nullable;

public record UsuarioUpdateDTO(

        @Nullable
        String login,

        @Nullable
        String nome,

        @Nullable
        TipoUsuario tipoUsuario,

        @Nullable
        Endereco endereco

) {
}
