package br.gov.pa.pge.biblback.service;


import br.gov.pa.pge.biblback.exception.LivroNaoEncontradoException;
import br.gov.pa.pge.biblback.model.Livro;
import br.gov.pa.pge.biblback.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository LIVRO_REPOSITORY;



    public Livro cadastrar(String nome, String autor, String isbn, Integer anoLancamento){

        Livro livro = new Livro(nome, autor, isbn, anoLancamento);
        return LIVRO_REPOSITORY.save(livro);
    }



    public Livro buscarPorId(Long id){
        return LIVRO_REPOSITORY.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    public void deletar(Long id){
        Livro livro = LIVRO_REPOSITORY.findById(id).orElseThrow(LivroNaoEncontradoException::new);
    }

    public List<Livro> listarTodos(){
        return LIVRO_REPOSITORY.findAll();
    }



}
