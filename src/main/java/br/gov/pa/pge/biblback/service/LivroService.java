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

    private final LivroRepository LIVRO_REPOSITORY;

    @Transactional
    public Livro cadastrar(String titulo, String autor, String isbn, Integer anoLancamento) {
        return cadastrar(titulo, autor, isbn, anoLancamento, null, null, null, null, null, null, null, null, null);
    }

    @Transactional
    public Livro cadastrar(String titulo, String autor, String isbn, Integer anoLancamento,
                           String registro, String classificacao, String tipoDocumental,
                           String localPublicacao, String editora, Integer edicao,
                           String idioma, Integer paginas, String descritores) {
        String finalIsbn = isbn;
        if (finalIsbn == null || finalIsbn.isBlank()) {
            finalIsbn = (registro != null && !registro.isBlank())
                    ? "REG-" + registro.trim()
                    : "AUTO-" + System.currentTimeMillis();
        }

        Livro livro = new Livro(titulo, autor, finalIsbn, anoLancamento);
        livro.setRegistro(registro);
        livro.setClassificacao(classificacao);
        livro.setTipoDocumental(tipoDocumental != null && !tipoDocumental.isBlank() ? tipoDocumental : "Livro");
        livro.setLocalPublicacao(localPublicacao);
        livro.setEditora(editora);
        livro.setEdicao(edicao);
        livro.setIdioma(idioma != null && !idioma.isBlank() ? idioma : "Português");
        livro.setPaginas(paginas);
        livro.setDescritores(descritores);
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
    public List<Livro> buscarPorTermo(String termo) {
        if (termo == null || termo.isBlank()) {
            return listarTodos();
        }
        return LIVRO_REPOSITORY.buscarPorTermo(termo.trim());
    }

    @Transactional(readOnly = true)
    public List<Livro> listarTodos() {
        return LIVRO_REPOSITORY.findAll();
    }

    @Transactional
    public Livro atualizar(Long id, String titulo, String autor, String isbn, Integer anoLancamento) {
        return atualizar(id, titulo, autor, isbn, anoLancamento, null, null, null, null, null, null, null, null, null);
    }

    @Transactional
    public Livro atualizar(Long id, String titulo, String autor, String isbn, Integer anoLancamento,
                           String registro, String classificacao, String tipoDocumental,
                           String localPublicacao, String editora, Integer edicao,
                           String idioma, Integer paginas, String descritores) {
        Livro livro = buscarPorId(id);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        if (isbn != null && !isbn.isBlank()) {
            livro.setIsbn(isbn);
        }
        livro.setAnoLancamento(anoLancamento);
        livro.setRegistro(registro);
        livro.setClassificacao(classificacao);
        livro.setTipoDocumental(tipoDocumental);
        livro.setLocalPublicacao(localPublicacao);
        livro.setEditora(editora);
        livro.setEdicao(edicao);
        livro.setIdioma(idioma);
        livro.setPaginas(paginas);
        livro.setDescritores(descritores);
        return LIVRO_REPOSITORY.save(livro);
    }

    @Transactional
    public void deletar(Long id) {
        Livro livro = buscarPorId(id);
        LIVRO_REPOSITORY.delete(livro);
    }
}
