package br.gov.pa.pge.biblback.repository;

import br.gov.pa.pge.biblback.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
