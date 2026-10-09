package br.gov.pa.pge.biblback.dto.reserva;

import br.gov.pa.pge.biblback.dto.livro.LivroResponse;
import br.gov.pa.pge.biblback.dto.usuario.UsuarioResponse;
import br.gov.pa.pge.biblback.enums.StatusReserva;
import br.gov.pa.pge.biblback.model.Reserva;

import java.time.LocalDate;

public record ReservaResponse(
        Long id,
        UsuarioResponse usuario,
        LivroResponse livro,
        LocalDate data,
        StatusReserva statusReserva
) {

    public static ReservaResponse from(Reserva reserva){
        return new ReservaResponse(reserva.getId(),
                UsuarioResponse.from(reserva.getUsuario()),
                LivroResponse.from(reserva.getLivro()),
                reserva.getLocalDate(),
                reserva.getStatusReserva());
    }
}
