package br.gov.pa.pge.biblback.dto.usuario;

import br.gov.pa.pge.biblback.enums.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequest(
        @NotBlank String cpf,
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String telefone,
        @NotNull TipoUsuario tipoUsuario,
        @NotBlank String senha
) {
}
