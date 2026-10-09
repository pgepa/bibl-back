package br.gov.pa.pge.biblback.exception;

public class LimiteRenovacaoEmprestimoAtingidoException extends RuntimeException {
    public LimiteRenovacaoEmprestimoAtingidoException(){
        super("Este empréstimo já atingiu o limite de renovações.");
    }
    public LimiteRenovacaoEmprestimoAtingidoException(String message) {
        super(message);
    }
}
