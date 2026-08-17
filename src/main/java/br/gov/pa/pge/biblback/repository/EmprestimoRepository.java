package br.gov.pa.pge.biblback.repository;

import br.gov.pa.pge.biblback.enums.StatusEmprestimo;
import br.gov.pa.pge.biblback.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    public List<Emprestimo> findByStatusEmprestimo(StatusEmprestimo statusEmprestimo);
    public byte countByUsuarioIdAndStatusEmprestimo(Long idUsuario, StatusEmprestimo statusEmprestimo);
    public boolean existsByUsuarioIdAndLivroIdAndStatusEmprestimo(Long idUsuario, Long livroId, StatusEmprestimo statusEmprestimo);
}
