package br.gov.pa.pge.biblback.dto;

import jakarta.validation.constraints.NotNull;

public record EmprestimoRequest(
        @NotNull Long livroId,
        @NotNull Long usuarioId
) {
}
