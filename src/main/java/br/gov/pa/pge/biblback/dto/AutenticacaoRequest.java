package br.gov.pa.pge.biblback.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AutenticacaoRequest(
        @NotBlank @Email String email,
        @NotBlank String senha
) {
}
