package br.gov.pa.pge.biblback.exception;

public class UsuarioNaoAtivoException extends RuntimeException {

    public UsuarioNaoAtivoException(){
        super("Este usuário não está ativo.");
    }
    public UsuarioNaoAtivoException(String message) {
        super(message);
    }
}
