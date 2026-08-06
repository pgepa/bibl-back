package br.gov.pa.pge.biblback.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradoException(){
        super("Usuário não encontrado");
    }
}
