package br.gov.pa.pge.biblback.repository;

import br.gov.pa.pge.biblback.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findByAutorContainingIgnoreCase(String autor);
    List<Livro> findByAnoLancamento(Integer anoLancamento);
    Optional<Livro> findByRegistro(String registro);
    Optional<Livro> findByIsbn(String isbn);

    @Query("SELECT l FROM Livro l WHERE " +
           "LOWER(l.titulo) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(l.autor) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(COALESCE(l.classificacao, '')) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(COALESCE(l.registro, '')) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(COALESCE(l.descritores, '')) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(COALESCE(l.isbn, '')) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Livro> buscarPorTermo(@Param("termo") String termo);
}
