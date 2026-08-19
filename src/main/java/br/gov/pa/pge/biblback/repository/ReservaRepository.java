package br.gov.pa.pge.biblback.repository;

import br.gov.pa.pge.biblback.enums.StatusReserva;
import br.gov.pa.pge.biblback.model.Livro;
import br.gov.pa.pge.biblback.model.Reserva;
import br.gov.pa.pge.biblback.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    public boolean existsByUsuarioAndLivroAndStatusReserva(Usuario usuario, Livro livro, StatusReserva statusReserva);
}
