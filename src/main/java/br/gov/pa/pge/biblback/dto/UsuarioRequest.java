package br.gov.pa.pge.biblback.dto;

import br.gov.pa.pge.biblback.enums.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record UsuarioRequest(
        @NotBlank String cpf,
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String telefone,
        @NotBlank TipoUsuario tipoUsuario,
        @NotBlank String sennha

) {
}
