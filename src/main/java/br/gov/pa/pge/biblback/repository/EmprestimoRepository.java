package br.gov.pa.pge.biblback.repository;

import br.gov.pa.pge.biblback.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

}
