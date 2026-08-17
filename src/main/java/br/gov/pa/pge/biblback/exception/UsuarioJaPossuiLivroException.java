package br.gov.pa.pge.biblback.exception;

public class UsuarioJaPossuiLivroException extends RuntimeException {
    public UsuarioJaPossuiLivroException(String message) {
        super(message);
    }
    public UsuarioJaPossuiLivroException(){
        super("Usuário já possui o livro.");
    }
}
