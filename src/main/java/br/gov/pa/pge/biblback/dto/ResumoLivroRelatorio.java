package br.gov.pa.pge.biblback.dto;

public record ResumoLivroRelatorio(
        Long livroId,
        String titulo,
        String registro,
        String autor,
        long quantidadeEmprestimos
) {
}
