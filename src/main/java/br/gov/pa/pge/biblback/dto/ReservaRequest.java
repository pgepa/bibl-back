package br.gov.pa.pge.biblback.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservaRequest(
        @NotNull Long idUsuario,
        @NotNull Long idLivro,
        @NotNull LocalDate data
        ) {
}
