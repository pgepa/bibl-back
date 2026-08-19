package br.gov.pa.pge.biblback.dto;

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
