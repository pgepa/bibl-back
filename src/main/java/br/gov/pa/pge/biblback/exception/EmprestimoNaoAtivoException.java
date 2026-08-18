package br.gov.pa.pge.biblback.exception;

public class EmprestimoNaoAtivoException extends RuntimeException {
    public EmprestimoNaoAtivoException(){
        super("Este empréstimo não está ativo.");
    }
    public EmprestimoNaoAtivoException(String message) {
        super(message);
    }
}
