package br.gov.pa.pge.biblback.exception;

public class LivroDisponivelParaEmprestimoException extends RuntimeException {
    public LivroDisponivelParaEmprestimoException() {
        super("Este livro está disponível para empréstimo imediato e não necessita de reserva.");
    }

    public LivroDisponivelParaEmprestimoException(String message) {
        super(message);
    }
}
