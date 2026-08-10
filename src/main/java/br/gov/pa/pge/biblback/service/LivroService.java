package br.gov.pa.pge.biblback.service;

import br.gov.pa.pge.biblback.exception.LivroNaoEncontradoException;
import br.gov.pa.pge.biblback.model.Livro;
import br.gov.pa.pge.biblback.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository LIVRO_REPOSITORY;

    @Transactional
    public Livro cadastrar(String titulo, String autor, String isbn, Integer anoLancamento) {
        Livro livro = new Livro(titulo, autor, isbn, anoLancamento);
        return LIVRO_REPOSITORY.save(livro);
    }

    @Transactional(readOnly = true)
    public Livro buscarPorId(Long id) {
        return LIVRO_REPOSITORY.findById(id).orElseThrow(LivroNaoEncontradoException::new);
    }

    @Transactional(readOnly = true)
    public List<Livro> buscarPorTitulo(String titulo) {
        return LIVRO_REPOSITORY.findByTituloContainingIgnoreCase(titulo.trim());
    }

    @Transactional(readOnly = true)
    public List<Livro> buscarPorAutor(String autor) {
        return LIVRO_REPOSITORY.findByAutorContainingIgnoreCase(autor.trim());
    }

    @Transactional(readOnly = true)
    public List<Livro> buscarPorAnoLancamento(Integer anoLancamento){
        return LIVRO_REPOSITORY.findByAnoLancamento(anoLancamento);
    }

    @Transactional(readOnly = true)
    public List<Livro> listarTodos() {
        return LIVRO_REPOSITORY.findAll();
    }

    @Transactional
    public Livro atualizar(Long id, String titulo, String autor, String isbn, Integer anoLancamento) {
        Livro livro = buscarPorId(id);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setIsbn(isbn);
        livro.setAnoLancamento(anoLancamento);
        return LIVRO_REPOSITORY.save(livro);
    }

    @Transactional
    public void deletar(Long id) {
        Livro livro = buscarPorId(id);
        LIVRO_REPOSITORY.delete(livro);
    }
}
