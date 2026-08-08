package br.gov.pa.pge.biblback.service;

import br.gov.pa.pge.biblback.enums.StatusEmprestimo;
import br.gov.pa.pge.biblback.exception.EmprestimoJaDevolvidoException;
import br.gov.pa.pge.biblback.exception.EmprestimoNaoEncontradoException;
import br.gov.pa.pge.biblback.exception.LivroIndisponivelException;
import br.gov.pa.pge.biblback.model.Emprestimo;
import br.gov.pa.pge.biblback.model.Livro;
import br.gov.pa.pge.biblback.model.Usuario;
import br.gov.pa.pge.biblback.repository.EmprestimoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository EMPRESTIMO_REPOSITORY;
    private final LivroService LIVRO_SERVICE;
    private final UsuarioService USUARIO_SERVICE;

    @Transactional
    public Emprestimo realizarEmprestimo(Long livroId, Long usuarioId, LocalDate dataPrevistaDevolucao) {

        Livro livro = LIVRO_SERVICE.buscarPorId(livroId);
        Usuario usuario = USUARIO_SERVICE.buscarPorId(usuarioId);
        LocalDate localDateNow = LocalDate.now();

        if(dataPrevistaDevolucao.isBefore(localDateNow)){
            throw new RuntimeException("A data de devolução não pode ser anterior à data do empréstimo.");
        }

        if (Boolean.FALSE.equals(livro.getDisponivel())) {
            throw new LivroIndisponivelException();
        }

        livro.setDisponivel(false);

        Emprestimo emprestimo = new Emprestimo(
                livro,
                usuario,
                LocalDate.now(),
                dataPrevistaDevolucao,
                StatusEmprestimo.ATIVO
        );

        return EMPRESTIMO_REPOSITORY.save(emprestimo);
    }

    @Transactional
    public Emprestimo devolver(Long emprestimoId) {

        Emprestimo emprestimo = buscarPorId(emprestimoId);

        if (emprestimo.getStatusEmprestimo() == StatusEmprestimo.CONCLUIDO) {
            throw new EmprestimoJaDevolvidoException();
        }

        emprestimo.setStatusEmprestimo(StatusEmprestimo.CONCLUIDO);
        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimo.getLivro().setDisponivel(true);

        return EMPRESTIMO_REPOSITORY.save(emprestimo);
    }

    @Transactional(readOnly = true)
    public Emprestimo buscarPorId(Long id) {
        return EMPRESTIMO_REPOSITORY.findById(id).orElseThrow(EmprestimoNaoEncontradoException::new);
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> buscarPorStatus(StatusEmprestimo statusEmprestimo){
        return EMPRESTIMO_REPOSITORY.findByStatus(statusEmprestimo);
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> listarTodos() {
        return EMPRESTIMO_REPOSITORY.findAll();
    }
}
