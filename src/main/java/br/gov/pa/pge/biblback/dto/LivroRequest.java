package br.gov.pa.pge.biblback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroRequest(
        @NotBlank(message = "O título é obrigatório") String titulo,
        @NotBlank(message = "O autor é obrigatório") String autor,
        String isbn,
        @NotNull(message = "O ano de lançamento é obrigatório") Integer anoLancamento,
        String registro,
        String classificacao,
        String tipoDocumental,
        String localPublicacao,
        String editora,
        Integer edicao,
        String idioma,
        Integer paginas,
        String descritores
) {
}
