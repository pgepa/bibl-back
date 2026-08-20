package br.gov.pa.pge.biblback.exception;

public class UsuarioJaAtivoException extends RuntimeException {
    public UsuarioJaAtivoException(){
        super("Este usuário já está ativo.");
    }
    public UsuarioJaAtivoException(String message) {
        super(message);
    }
}
