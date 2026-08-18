package br.gov.pa.pge.biblback.dto;

public record RelatorioEmprestimoResponse(
        long emprestimosTotais,
        long emprestimosAtivos,
        long emprestimosConluidos,
        long emprestimosAtrasados
) {
}
