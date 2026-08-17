package br.gov.pa.pge.biblback.exception;

public class LimiteEmprestimoAtingidoException extends RuntimeException {
    public LimiteEmprestimoAtingidoException(){
        super("Limite de empréstimo atingido.");
    }
    public LimiteEmprestimoAtingidoException(String message) {
        super(message);
    }
}
