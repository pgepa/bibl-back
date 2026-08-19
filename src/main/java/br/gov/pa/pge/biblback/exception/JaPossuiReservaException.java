package br.gov.pa.pge.biblback.exception;

public class JaPossuiReservaException extends RuntimeException {
    public JaPossuiReservaException(){
        super("Você já possui uma reserva.");
    }
    public JaPossuiReservaException(String message) {
        super(message);
    }
}
