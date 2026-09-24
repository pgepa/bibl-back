package br.gov.pa.pge.biblback.dto;

import br.gov.pa.pge.biblback.enums.StatusEmprestimo;
import br.gov.pa.pge.biblback.model.Emprestimo;

import java.time.LocalDate;

public record EmprestimoResponse(
        Long id,
        LivroResponse livro,
        UsuarioResponse usuario,
        LocalDate dataEmprestimo,
        LocalDate dataPrevistaDevolucao,
        LocalDate dataDevolucao,
        StatusEmprestimo statusEmprestimo,
        int quantidadeRenovacaoEmprestimo,
        String idTransacao,
        String nomeFuncionario
) {
    public static EmprestimoResponse from(Emprestimo emprestimo) {
        return new EmprestimoResponse(
                emprestimo.getId(),
                LivroResponse.from(emprestimo.getLivro()),
                UsuarioResponse.from(emprestimo.getUsuario()),
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataPrevistaDevolucao(),
                emprestimo.getDataDevolucao(),
                emprestimo.getStatusEmprestimo(),
                emprestimo.getQuantidadeRenovacaoEmprestimo(),
                emprestimo.getIdTransacao(),
                emprestimo.getNomeFuncionario()
        );
    }
}
