package br.gov.pa.pge.biblback.enums;

import lombok.Getter;

@Getter
public enum StatusReserva {
    AGUARDANDO(1),
    CONCLUIDA(2),
    CANCELADO(3);


    private final Integer CODIGO;

    StatusReserva(Integer codigo){
        this.CODIGO = codigo;
    }
}
