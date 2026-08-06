package br.gov.pa.pge.biblback.exception;

public class EmprestimoNaoEncontradoException extends RuntimeException {

    public EmprestimoNaoEncontradoException() {
        super("Empréstimo não encontrado");
    }

    public EmprestimoNaoEncontradoException(String message) {
        super(message);
    }
}
