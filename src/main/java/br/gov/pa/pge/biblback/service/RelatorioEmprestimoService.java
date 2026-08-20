package br.gov.pa.pge.biblback.service;

import br.gov.pa.pge.biblback.dto.RelatorioEmprestimoResponse;
import br.gov.pa.pge.biblback.enums.StatusEmprestimo;
import br.gov.pa.pge.biblback.repository.EmprestimoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RelatorioEmprestimoService {

    private final EmprestimoRepository EMPRESTIMO_REPOSITORY;

    @Transactional(readOnly = true)
    public RelatorioEmprestimoResponse gerarRelatorioEmprestimo(){

        long totalEmprestimo = EMPRESTIMO_REPOSITORY.count();
        long emprestimosAtivos = EMPRESTIMO_REPOSITORY
                .findByStatusEmprestimo(StatusEmprestimo.ATIVO).size();
        long emprestimosConcluidos = EMPRESTIMO_REPOSITORY
                .findByStatusEmprestimo(StatusEmprestimo.CONCLUIDO).size();
        long emprestimosAtrasados = EMPRESTIMO_REPOSITORY
                .findByStatusEmprestimo(StatusEmprestimo.ATRASADO).size();

        return new RelatorioEmprestimoResponse(
                totalEmprestimo,
                emprestimosAtivos,
                emprestimosConcluidos,
                emprestimosAtrasados);
    }
}
