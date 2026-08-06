package br.gov.pa.pge.biblback.repository;

import br.gov.pa.pge.biblback.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {

}
