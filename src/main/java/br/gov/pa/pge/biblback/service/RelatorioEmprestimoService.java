package br.gov.pa.pge.biblback.service;

import br.gov.pa.pge.biblback.dto.ItemRelatorioEmprestimo;
import br.gov.pa.pge.biblback.dto.RelatorioEmprestimoResponse;
import br.gov.pa.pge.biblback.dto.ResumoLivroRelatorio;
import br.gov.pa.pge.biblback.dto.ResumoUsuarioRelatorio;
import br.gov.pa.pge.biblback.enums.StatusEmprestimo;
import br.gov.pa.pge.biblback.model.Emprestimo;
import br.gov.pa.pge.biblback.repository.EmprestimoRepository;
import br.gov.pa.pge.biblback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatorioEmprestimoService {

    private final EmprestimoRepository EMPRESTIMO_REPOSITORY;
    private final UsuarioRepository USUARIO_REPOSITORY;

    @Transactional(readOnly = true)
    public RelatorioEmprestimoResponse gerarRelatorioEmprestimo() {
        return gerarRelatorioEmprestimo(null, null, null, null);
    }

    @Transactional(readOnly = true)
    public RelatorioEmprestimoResponse gerarRelatorioEmprestimo(
            Long usuarioId,
            LocalDate dataInicio,
            LocalDate dataFim,
            StatusEmprestimo status) {

        List<Emprestimo> todos = EMPRESTIMO_REPOSITORY.findAll();

        List<Emprestimo> filtrados = todos.stream()
                .filter(e -> usuarioId == null || (e.getUsuario() != null && Objects.equals(e.getUsuario().getId(), usuarioId)))
                .filter(e -> dataInicio == null || (e.getDataEmprestimo() != null && !e.getDataEmprestimo().isBefore(dataInicio)))
                .filter(e -> dataFim == null || (e.getDataEmprestimo() != null && !e.getDataEmprestimo().isAfter(dataFim)))
                .filter(e -> status == null || e.getStatusEmprestimo() == status)
                .sorted(Comparator.comparing(Emprestimo::getDataEmprestimo, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();

        long totalEmprestimo = filtrados.size();
        long emprestimosAtivos = filtrados.stream().filter(e -> e.getStatusEmprestimo() == StatusEmprestimo.ATIVO).count();
        long emprestimosConcluidos = filtrados.stream().filter(e -> e.getStatusEmprestimo() == StatusEmprestimo.CONCLUIDO).count();
        long emprestimosAtrasados = filtrados.stream().filter(e -> e.getStatusEmprestimo() == StatusEmprestimo.ATRASADO).count();

        long totalUsuariosAtendidos = filtrados.stream()
                .map(e -> e.getUsuario() != null ? e.getUsuario().getId() : null)
                .filter(Objects::nonNull)
                .distinct()
                .count();

        long totalObrasDistintas = filtrados.stream()
                .map(e -> e.getLivro() != null ? e.getLivro().getId() : null)
                .filter(Objects::nonNull)
                .distinct()
                .count();

        List<ItemRelatorioEmprestimo> itens = filtrados.stream()
                .map(e -> new ItemRelatorioEmprestimo(
                        e.getId(),
                        e.getIdTransacao(),
                        e.getLivro() != null ? e.getLivro().getId() : null,
                        e.getLivro() != null ? e.getLivro().getTitulo() : "-",
                        e.getLivro() != null ? e.getLivro().getRegistro() : null,
                        e.getLivro() != null ? e.getLivro().getAutor() : "-",
                        e.getUsuario() != null ? e.getUsuario().getId() : null,
                        e.getUsuario() != null ? e.getUsuario().getNome() : "-",
                        e.getUsuario() != null ? e.getUsuario().getMatricula() : null,
                        e.getUsuario() != null ? e.getUsuario().getSetor() : null,
                        e.getUsuario() != null ? e.getUsuario().getCpf() : null,
                        e.getDataEmprestimo(),
                        e.getDataPrevistaDevolucao(),
                        e.getDataDevolucao(),
                        e.getStatusEmprestimo(),
                        e.getQuantidadeRenovacaoEmprestimo(),
                        e.getNomeFuncionario()
                ))
                .toList();

        // Ranking de leitores mais ativos no período
        Map<Long, List<Emprestimo>> porUsuario = filtrados.stream()
                .filter(e -> e.getUsuario() != null && e.getUsuario().getId() != null)
                .collect(Collectors.groupingBy(e -> e.getUsuario().getId()));

        List<ResumoUsuarioRelatorio> rankingUsuarios = porUsuario.entrySet().stream()
                .map(entry -> {
                    Emprestimo sample = entry.getValue().get(0);
                    return new ResumoUsuarioRelatorio(
                            entry.getKey(),
                            sample.getUsuario().getNome(),
                            sample.getUsuario().getMatricula(),
                            sample.getUsuario().getSetor(),
                            entry.getValue().size()
                    );
                })
                .sorted(Comparator.comparingLong(ResumoUsuarioRelatorio::quantidadeEmprestimos).reversed())
                .limit(5)
                .toList();

        // Ranking de livros mais emprestados no período
        Map<Long, List<Emprestimo>> porLivro = filtrados.stream()
                .filter(e -> e.getLivro() != null && e.getLivro().getId() != null)
                .collect(Collectors.groupingBy(e -> e.getLivro().getId()));

        List<ResumoLivroRelatorio> rankingLivros = porLivro.entrySet().stream()
                .map(entry -> {
                    Emprestimo sample = entry.getValue().get(0);
                    return new ResumoLivroRelatorio(
                            entry.getKey(),
                            sample.getLivro().getTitulo(),
                            sample.getLivro().getRegistro(),
                            sample.getLivro().getAutor(),
                            entry.getValue().size()
                    );
                })
                .sorted(Comparator.comparingLong(ResumoLivroRelatorio::quantidadeEmprestimos).reversed())
                .limit(5)
                .toList();

        String usuarioNomeFiltro = null;
        if (usuarioId != null) {
            usuarioNomeFiltro = USUARIO_REPOSITORY.findById(usuarioId)
                    .map(u -> u.getNome())
                    .orElse(null);
        }

        return new RelatorioEmprestimoResponse(
                totalEmprestimo,
                emprestimosAtivos,
                emprestimosConcluidos,
                emprestimosAtrasados,
                totalUsuariosAtendidos,
                totalObrasDistintas,
                dataInicio,
                dataFim,
                usuarioId,
                usuarioNomeFiltro,
                itens,
                rankingUsuarios,
                rankingLivros
        );
    }
}
