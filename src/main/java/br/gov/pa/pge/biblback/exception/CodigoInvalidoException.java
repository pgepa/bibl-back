package br.gov.pa.pge.biblback.exception;

public class CodigoInvalidoException extends RuntimeException {
    public CodigoInvalidoException(){
        super("Código inválido.");
    }
    public CodigoInvalidoException(String message) {
        super(message);
    }
}
