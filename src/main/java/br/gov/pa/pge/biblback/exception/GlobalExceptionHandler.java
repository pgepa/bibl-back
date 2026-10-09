package br.gov.pa.pge.biblback.exception;

import br.gov.pa.pge.biblback.dto.erro.ErroResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            LivroNaoEncontradoException.class,
            UsuarioNaoEncontradoException.class,
            EmprestimoNaoEncontradoException.class
    })
    public ResponseEntity<ErroResponse> handleNotFound(
            RuntimeException ex,
            HttpServletRequest request) {

        return build(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler({
            CodigoInvalidoException.class,
            DataDevolucaoInvalidaException.class,
            DataReservaInvalidaException.class
    })
    public ResponseEntity<ErroResponse> handleBadRequest(
            RuntimeException ex,
            HttpServletRequest request) {

        return build(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField() + ": " + error.getDefaultMessage()
                )
                .collect(Collectors.joining("; "));

        return build(
                HttpStatus.BAD_REQUEST,
                mensagem,
                request.getRequestURI()
        );
    }
    @ExceptionHandler({
            LivroIndisponivelException.class,
            LivroDisponivelParaEmprestimoException.class,
            EmprestimoJaDevolvidoException.class,
            EmprestimoNaoAtivoException.class,
            JaPossuiReservaException.class,
            LimiteEmprestimoAtingidoException.class,
            LimiteRenovacaoEmprestimoAtingidoException.class,
            UsuarioJaPossuiLivroException.class,
            UsuarioNaoAtivoException.class,
            UsuarioJaAtivoException.class
    })
    public ResponseEntity<ErroResponse> handleConflict(
            RuntimeException ex,
            HttpServletRequest request) {

        return build(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    // captura credenciais incorretas e retorna 401 unauthorized
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroResponse> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request) {
        return build(
                HttpStatus.UNAUTHORIZED,
                "Credenciais inválidas: e-mail ou senha incorretos.",
                request.getRequestURI()
        );
    }

    // captura falhas gerais de autenticacao e retorna 401 unauthorized
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErroResponse> handleAuthenticationException(
            AuthenticationException ex,
            HttpServletRequest request) {
        return build(
                HttpStatus.UNAUTHORIZED,
                "Falha na autenticação: " + ex.getMessage(),
                request.getRequestURI()
        );
    }

    // captura tentativas de acesso sem permissao e retorna 403 forbidden
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroResponse> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request) {
        return build(
                HttpStatus.FORBIDDEN,
                "Acesso negado: você não tem permissão para acessar este recurso.",
                request.getRequestURI()
        );
    }

    // captura erros de chave unica ou integridade relacional do banco
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponse> handleDataIntegrity(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        return build(
                HttpStatus.CONFLICT,
                "Violação de integridade dos dados",
                request.getRequestURI()
        );
    }

    // captura excecoes nao tratadas e retorna 500 internal server error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno no servidor: " + ex.getMessage(),
                request.getRequestURI()
        );
    }

    private ResponseEntity<ErroResponse> build(
            HttpStatus status,
            String mensagem,
            String caminho) {

        ErroResponse body = new ErroResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                mensagem,
                caminho
        );

        return ResponseEntity
                .status(status)
                .body(body);
    }
}