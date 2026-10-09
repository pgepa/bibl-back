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

    private final EmprestimoRepository emprestimoRepository;

    // gera metricas consolidadas executando queries de contagem direta no banco
    @Transactional(readOnly = true)
    public RelatorioEmprestimoResponse gerarRelatorioEmprestimo() {
        // executa count no banco de dados para evitar carregar entidades na memoria
        long totalEmprestimo = emprestimoRepository.count();
        long emprestimosAtivos = emprestimoRepository.countByStatusEmprestimo(StatusEmprestimo.ATIVO);
        long emprestimosConcluidos = emprestimoRepository.countByStatusEmprestimo(StatusEmprestimo.CONCLUIDO);
        long emprestimosAtrasados = emprestimoRepository.countByStatusEmprestimo(StatusEmprestimo.ATRASADO);

        return new RelatorioEmprestimoResponse(
                totalEmprestimo,
                emprestimosAtivos,
                emprestimosConcluidos,
                emprestimosAtrasados
        );
    }
}
