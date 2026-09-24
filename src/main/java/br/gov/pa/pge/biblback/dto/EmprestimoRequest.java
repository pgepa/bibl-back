package br.gov.pa.pge.biblback.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import java.util.List;

public record EmprestimoRequest(
        Long livroId,
        List<Long> livroIds,
        @NotNull Long usuarioId,
        @NotNull LocalDate dataPrevistaDevolucao,
        String nomeFuncionario
) {
}
