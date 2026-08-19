package br.gov.pa.pge.biblback.exception;

public class DataReservaInvalidaException extends RuntimeException {

    public DataReservaInvalidaException(){
        super("A data da reserva não pode ser anterior à data de realização.");
    }
    public DataReservaInvalidaException(String message) {
        super(message);
    }
}
