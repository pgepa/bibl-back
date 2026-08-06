package br.gov.pa.pge.biblback.service;

import br.gov.pa.pge.biblback.exception.LivroIndisponivelException;
import br.gov.pa.pge.biblback.exception.LivroNaoEncontradoException;
import br.gov.pa.pge.biblback.model.Emprestimo;
import br.gov.pa.pge.biblback.model.Livro;
import br.gov.pa.pge.biblback.repository.EmprestimoRepository;
import br.gov.pa.pge.biblback.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository EMPRESTIMO_REPOSITORY;
    private final LivroRepository LIVRO_REPOSITORY;


    public Emprestimo salvarrEmprestimo(Emprestimo emprestimo){

        Livro livro = LIVRO_REPOSITORY.findById(emprestimo.getLivro().getId()).orElseThrow(LivroNaoEncontradoException::new);

        if(!livro.getDisponivel()){
            throw new LivroIndisponivelException();
        }

        livro.setDisponivel(false);

        LIVRO_REPOSITORY.save(livro);

        return EMPRESTIMO_REPOSITORY.save(emprestimo);
    }


    public List<Emprestimo> listarTdos(){
        return EMPRESTIMO_REPOSITORY.findAll();
    }
}
