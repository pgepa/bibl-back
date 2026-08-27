package br.gov.pa.pge.biblback.repository;

import br.gov.pa.pge.biblback.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public List<Usuario> findByNomeContainingIgnoreCase(String nome);
    public Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByEmail(String email);

}
