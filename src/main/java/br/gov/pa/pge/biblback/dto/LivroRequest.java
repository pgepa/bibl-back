package br.gov.pa.pge.biblback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroRequest(
        @NotBlank String titulo,
        @NotBlank String autor,
        @NotBlank String isbn,
        @NotNull Integer anoLancamento
) {
}
