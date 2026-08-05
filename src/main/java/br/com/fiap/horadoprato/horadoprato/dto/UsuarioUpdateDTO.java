package br.com.fiap.horadoprato.horadoprato.dto;

import br.com.fiap.horadoprato.horadoprato.model.Endereco;
import br.com.fiap.horadoprato.horadoprato.model.TipoUsuario;
import jakarta.annotation.Nullable;

public record UsuarioUpdateDTO(

        @Nullable
        String nome,

        @Nullable
        String login,

        @Nullable
        TipoUsuario tipoUsuario,

        @Nullable
        Endereco endereco

) {
}
