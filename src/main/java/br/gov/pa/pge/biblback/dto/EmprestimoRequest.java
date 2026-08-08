package br.gov.pa.pge.biblback.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EmprestimoRequest(
        @NotNull Long livroId,
        @NotNull Long usuarioId,
        @NotNull LocalDate dataPrevistaDevolucao
) {
}
