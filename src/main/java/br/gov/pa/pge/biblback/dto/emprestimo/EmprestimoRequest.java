package br.gov.pa.pge.biblback.dto.emprestimo;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EmprestimoRequest(
        @NotNull Long livroId,
        @NotNull Long usuarioId,
        @NotNull LocalDate dataPrevistaDevolucao
) {
}
