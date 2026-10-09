package br.gov.pa.pge.biblback.repository;

import br.gov.pa.pge.biblback.enums.StatusEmprestimo;
import br.gov.pa.pge.biblback.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByStatusEmprestimo(StatusEmprestimo statusEmprestimo);
    long countByStatusEmprestimo(StatusEmprestimo statusEmprestimo);
    long countByUsuarioIdAndStatusEmprestimo(Long idUsuario, StatusEmprestimo statusEmprestimo);
    boolean existsByUsuarioIdAndLivroIdAndStatusEmprestimo(Long idUsuario, Long livroId, StatusEmprestimo statusEmprestimo);
    List<Emprestimo> findByStatusEmprestimoAndDataPrevistaDevolucaoBefore(StatusEmprestimo status, LocalDate data);
}
