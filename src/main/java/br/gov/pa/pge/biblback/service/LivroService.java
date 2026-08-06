package br.gov.pa.pge.biblback.service;

import br.gov.pa.pge.biblback.exception.LivroNaoEncontradoException;
import br.gov.pa.pge.biblback.model.Livro;
import br.gov.pa.pge.biblback.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    @Transactional
    public Livro cadastrar(String titulo, String autor, String isbn, Integer anoLancamento) {
        Livro livro = new Livro(titulo, autor, isbn, anoLancamento);
        return livroRepository.save(livro);
    }

    @Transactional(readOnly = true)
    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id).orElseThrow(LivroNaoEncontradoException::new);
    }

    @Transactional(readOnly = true)
    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    @Transactional
    public Livro atualizar(Long id, String titulo, String autor, String isbn, Integer anoLancamento) {
        Livro livro = buscarPorId(id);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setIsbn(isbn);
        livro.setAnoLancamento(anoLancamento);
        return livroRepository.save(livro);
    }

    @Transactional
    public void deletar(Long id) {
        Livro livro = buscarPorId(id);
        livroRepository.delete(livro);
    }
}
