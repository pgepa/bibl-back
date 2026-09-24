package br.gov.pa.pge.biblback.dto;

import java.time.LocalDate;
import java.util.List;

public record RelatorioEmprestimoResponse(
        long emprestimosTotais,
        long emprestimosAtivos,
        long emprestimosConcluidos,
        long emprestimosAtrasados,
        long totalUsuariosAtendidos,
        long totalObrasDistintas,
        LocalDate dataInicio,
        LocalDate dataFim,
        Long usuarioIdFiltro,
        String usuarioNomeFiltro,
        List<ItemRelatorioEmprestimo> itens,
        List<ResumoUsuarioRelatorio> rankingUsuarios,
        List<ResumoLivroRelatorio> rankingLivros
) {
}
