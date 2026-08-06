package br.gov.pa.pge.biblback.exception;

public class LivroIndisponivelException extends RuntimeException {
    public LivroIndisponivelException(String message) {
        super(message);
    }

    public LivroIndisponivelException(){
        super("livro indisponivel para emprestimo");
    }
}
