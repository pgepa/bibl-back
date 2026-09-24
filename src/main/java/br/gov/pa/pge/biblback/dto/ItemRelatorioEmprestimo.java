package br.gov.pa.pge.biblback.dto;

import br.gov.pa.pge.biblback.enums.StatusEmprestimo;
import java.time.LocalDate;

public record ItemRelatorioEmprestimo(
        Long id,
        String idTransacao,
        Long livroId,
        String livroTitulo,
        String livroRegistro,
        String livroAutor,
        Long usuarioId,
        String usuarioNome,
        String usuarioMatricula,
        String usuarioSetor,
        String usuarioCpf,
        LocalDate dataEmprestimo,
        LocalDate dataPrevistaDevolucao,
        LocalDate dataDevolucao,
        StatusEmprestimo statusEmprestimo,
        int quantidadeRenovacoes,
        String nomeFuncionario
) {
}
