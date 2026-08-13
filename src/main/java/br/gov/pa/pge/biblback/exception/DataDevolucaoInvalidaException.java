package br.gov.pa.pge.biblback.exception;

public class DataDevolucaoInvalidaException extends RuntimeException {
    public DataDevolucaoInvalidaException(String message) {
        super(message);
    }

    public DataDevolucaoInvalidaException(){
        super("A data de devolução não pode ser anterior à data do empréstimo.");
    }
}
