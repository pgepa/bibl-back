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

    private final EmprestimoRepository emprestimoRepository;
    private final LivroService livroService;
    private final UsuarioService usuarioService;

    @Transactional
    public Emprestimo realizarEmprestimo(Long livroId, Long usuarioId) {
        Livro livro = livroService.buscarPorId(livroId);
        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        if (Boolean.FALSE.equals(livro.getDisponivel())) {
            throw new LivroIndisponivelException();
        }

        livro.setDisponivel(false);

        Emprestimo emprestimo = new Emprestimo(
                livro,
                usuario,
                LocalDate.now(),
                StatusEmprestimo.ATIVO
        );

        return emprestimoRepository.save(emprestimo);
    }

    @Transactional
    public Emprestimo devolver(Long emprestimoId) {
        Emprestimo emprestimo = buscarPorId(emprestimoId);

        if (emprestimo.getStatusEmprestimo() == StatusEmprestimo.DEVOLVIDO) {
            throw new EmprestimoJaDevolvidoException();
        }

        emprestimo.setStatusEmprestimo(StatusEmprestimo.DEVOLVIDO);
        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimo.getLivro().setDisponivel(true);

        return emprestimoRepository.save(emprestimo);
    }

    @Transactional(readOnly = true)
    public Emprestimo buscarPorId(Long id) {
        return emprestimoRepository.findById(id).orElseThrow(EmprestimoNaoEncontradoException::new);
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }
}
