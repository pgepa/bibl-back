package br.gov.pa.pge.biblback.service;

import br.gov.pa.pge.biblback.enums.StatusEmprestimo;
import br.gov.pa.pge.biblback.exception.*;
import br.gov.pa.pge.biblback.model.Emprestimo;
import br.gov.pa.pge.biblback.model.Livro;
import br.gov.pa.pge.biblback.model.Usuario;
import br.gov.pa.pge.biblback.repository.EmprestimoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroService livroService;
    private final UsuarioService usuarioService;
    private static final byte LIMITE_EMPRESTIMO_ATIVO = 3;

    // valida regras de negocio e cria novo emprestimo
    @Transactional
    public Emprestimo realizarEmprestimo(Long livroId, Long usuarioId, LocalDate dataPrevistaDevolucao) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);
        Livro livro = livroService.buscarPorId(livroId);
        long quantidadeEmprestimo = emprestimoRepository.countByUsuarioIdAndStatusEmprestimo(usuario.getId(), StatusEmprestimo.ATIVO);
        boolean jaPossuiLivroEmprestado = emprestimoRepository.existsByUsuarioIdAndLivroIdAndStatusEmprestimo(usuario.getId(), livro.getId(), StatusEmprestimo.ATIVO);
        LocalDate localDateNow = LocalDate.now();

        // verifica se o usuario atingiu o limite maximo de emprestimos ativos
        if (quantidadeEmprestimo >= LIMITE_EMPRESTIMO_ATIVO) {
            throw new LimiteEmprestimoAtingidoException();
        }
        // impede emprestimo duplicado do mesmo livro para o usuario
        if (jaPossuiLivroEmprestado) {
            throw new UsuarioJaPossuiLivroException();
        }
        // garante que a data prevista de devolucao seja futura
        if (dataPrevistaDevolucao.isBefore(localDateNow)) {
           throw new DataDevolucaoInvalidaException();
        }
        // verifica se o livro esta disponivel no acervo
        if (Boolean.FALSE.equals(livro.getDisponivel())) {
            throw new LivroIndisponivelException();
        }

        // marca o exemplar como indisponivel
        livro.setDisponivel(false);

        Emprestimo emprestimo = new Emprestimo(
                livro,
                usuario,
                LocalDate.now(),
                dataPrevistaDevolucao,
                StatusEmprestimo.ATIVO
        );

        return emprestimoRepository.save(emprestimo);
    }

    // finaliza o emprestimo e libera o livro novamente no acervo
    @Transactional
    public Emprestimo devolver(Long emprestimoId) {
        Emprestimo emprestimo = buscarPorId(emprestimoId);

        if (emprestimo.getStatusEmprestimo() == StatusEmprestimo.CONCLUIDO) {
            throw new EmprestimoJaDevolvidoException();
        }

        // atualiza status, registra data de entrega e torna livro disponivel
        emprestimo.setStatusEmprestimo(StatusEmprestimo.CONCLUIDO);
        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimo.getLivro().setDisponivel(true);

        return emprestimoRepository.save(emprestimo);
    }

    @Transactional(readOnly = true)
    public Emprestimo buscarPorId(Long id) {
        return emprestimoRepository.findById(id).orElseThrow(EmprestimoNaoEncontradoException::new);
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> buscarPorStatus(StatusEmprestimo statusEmprestimo){
        return emprestimoRepository.findByStatusEmprestimo(statusEmprestimo);
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    // rotina agendada para atualizar emprestimos vencidos para status atrasado
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void verificarEmprestimosAtrasados() {
        // busca emprestimos ativos cuja data prevista de devolucao ja expirou
        List<Emprestimo> emprestimosAtrasados = emprestimoRepository
                .findByStatusEmprestimoAndDataPrevistaDevolucaoBefore(StatusEmprestimo.ATIVO, LocalDate.now());

        for (Emprestimo emprestimo : emprestimosAtrasados) {
            emprestimo.setStatusEmprestimo(StatusEmprestimo.ATRASADO);
        }

        emprestimoRepository.saveAll(emprestimosAtrasados);
    }

    // estende o prazo de devolucao em 7 dias respeitando limite de renovacao
    @Transactional
    public Emprestimo renovarEmprestimo(Long id) {
        Emprestimo emprestimo = buscarPorId(id);

        // apenas emprestimos ativos podem ser renovados
        if (emprestimo.getStatusEmprestimo() != StatusEmprestimo.ATIVO) {
            throw new EmprestimoNaoAtivoException();
        }

        // valida se ja atingiu o maximo de renovacoes permitidas
        final byte LIMITE_RENOVACAO_EMPRESTIMO = 1;
        if (emprestimo.getQuantidadeRenovacaoEmprestimo() >= LIMITE_RENOVACAO_EMPRESTIMO) {
            throw new LimiteRenovacaoEmprestimoAtingidoException();
        }

        // adiciona 7 dias a data prevista e incrementa contador
        emprestimo.setDataPrevistaDevolucao(emprestimo.getDataPrevistaDevolucao().plusDays(7));
        emprestimo.setQuantidadeRenovacaoEmprestimo(1);
        return emprestimoRepository.save(emprestimo);
    }
}

