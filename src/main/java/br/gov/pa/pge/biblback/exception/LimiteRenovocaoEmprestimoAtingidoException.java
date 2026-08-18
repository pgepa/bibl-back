package br.gov.pa.pge.biblback.exception;

public class LimiteRenovocaoEmprestimoAtingidoException extends RuntimeException {
    public LimiteRenovocaoEmprestimoAtingidoException(){
        super("Este empréstimo já atingiu o limite de renovações.");
    }
    public LimiteRenovocaoEmprestimoAtingidoException(String message) {
        super(message);
    }
}
