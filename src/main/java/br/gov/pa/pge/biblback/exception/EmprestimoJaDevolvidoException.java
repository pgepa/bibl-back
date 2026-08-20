package br.gov.pa.pge.biblback.exception;

public class EmprestimoJaDevolvidoException extends RuntimeException {

    public EmprestimoJaDevolvidoException() {
        super("Empréstimo já foi concluído");
    }

    public EmprestimoJaDevolvidoException(String message) {
        super(message);
    }
}
