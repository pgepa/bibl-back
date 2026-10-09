package br.gov.pa.pge.biblback.repository;

import br.gov.pa.pge.biblback.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
    Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByEmail(String email);

}
