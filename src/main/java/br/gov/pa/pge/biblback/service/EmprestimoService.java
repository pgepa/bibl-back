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

    private final EmprestimoRepository EMPRESTIMO_REPOSITORY;
    private final LivroService LIVRO_SERVICE;
    private final UsuarioService USUARIO_SERVICE;
    private static final byte LIMITE_EMPRESTIMO_ATIVO = 3;
    private final int LIMITE_RENOVACAO_EMPRESTIMO = 1;


    @Transactional
    public Emprestimo realizarEmprestimo(Long livroId, Long usuarioId, LocalDate dataPrevistaDevolucao) {

        Usuario usuario = USUARIO_SERVICE.buscarPorId(usuarioId);
        Livro livro = LIVRO_SERVICE.buscarPorId(livroId);
        byte quantidadeEmprestimo = EMPRESTIMO_REPOSITORY.countByUsuarioIdAndStatusEmprestimo(usuario.getId(), StatusEmprestimo.ATIVO);
        boolean jaPossuiLivroEmprestado = EMPRESTIMO_REPOSITORY.existsByUsuarioIdAndLivroIdAndStatusEmprestimo(usuario.getId(), livro.getId(), StatusEmprestimo.ATIVO);
        LocalDate localDateNow = LocalDate.now();

        if(quantidadeEmprestimo >= LIMITE_EMPRESTIMO_ATIVO){
            throw new LimiteEmprestimoAtingidoException();
        }
        if(jaPossuiLivroEmprestado){
            throw new UsuarioJaPossuiLivroException();
        }
        if(dataPrevistaDevolucao.isBefore(localDateNow)){
           throw new DataDevolucaoInvalidaException();
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
        return EMPRESTIMO_REPOSITORY.findByStatusEmprestimo(statusEmprestimo);
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> listarTodos() {
        return EMPRESTIMO_REPOSITORY.findAll();
    }

    @Scheduled(fixedRate =  60000)
    public void verificarEmprestimosAtrasados(){

        List<Emprestimo> emprestimosAtrasados = EMPRESTIMO_REPOSITORY
                .findByStatusEmprestimoAndDataPrevistaDevolucaoBefore(StatusEmprestimo.ATIVO, LocalDate.now());

         for(Emprestimo emprestimo : emprestimosAtrasados){
             emprestimo.setStatusEmprestimo(StatusEmprestimo.ATRASADO);
         }

         EMPRESTIMO_REPOSITORY.saveAll(emprestimosAtrasados);
    }

    @Transactional
    public Emprestimo renovarEmprestimo(Long id){

        Emprestimo emprestimo = buscarPorId(id);

        if(emprestimo.getStatusEmprestimo() != StatusEmprestimo.ATIVO){
            throw new EmprestimoNaoAtivoException();
        }

        if(emprestimo.getQuantidadeRenovacaoEmprestimo() >= LIMITE_RENOVACAO_EMPRESTIMO){
            throw new LimiteRenovocaoEmprestimoAtingidoException();
        }

        emprestimo.setDataPrevistaDevolucao(emprestimo.getDataPrevistaDevolucao().plusDays(7));
        emprestimo.setQuantidadeRenovacaoEmprestimo(1);
        return EMPRESTIMO_REPOSITORY.save(emprestimo);
    }
}

