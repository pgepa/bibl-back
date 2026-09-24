package br.gov.pa.pge.biblback.repository;

import br.gov.pa.pge.biblback.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
    Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByMatricula(String matricula);

    @org.springframework.data.jpa.repository.Query("SELECT u FROM Usuario u WHERE " +
           "LOWER(u.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(u.cpf) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(COALESCE(u.matricula, '')) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(COALESCE(u.setor, '')) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Usuario> buscarPorTermo(@org.springframework.data.repository.query.Param("termo") String termo);
}
