package br.gov.pa.pge.biblback.dto;

public record ResumoUsuarioRelatorio(
        Long usuarioId,
        String nome,
        String matricula,
        String setor,
        long quantidadeEmprestimos
) {
}
