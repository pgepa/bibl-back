package br.gov.pa.pge.biblback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroRequest(
        @NotBlank (message = "O título é obrigatório") String titulo,
        @NotBlank(message = "O autor é obrigatório") String autor,
        @NotBlank (message = "O ISBN é obrigatório") String isbn,
        @NotNull (message = "O ano de lançamento é obrigatório") Integer anoLancamento
) {
}
