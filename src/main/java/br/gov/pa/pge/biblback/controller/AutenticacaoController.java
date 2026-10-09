package br.gov.pa.pge.biblback.controller;

import br.gov.pa.pge.biblback.dto.AutenticacaoRequest;
import br.gov.pa.pge.biblback.dto.TokenResponse;
import br.gov.pa.pge.biblback.model.Usuario;
import br.gov.pa.pge.biblback.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    // autentica credenciais e retorna token jwt
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody AutenticacaoRequest authRequest) {
        // cria token de autenticacao com email e senha informados
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.senha());

        // valida credenciais atraves do authentication manager
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // gera token jwt para o usuario autenticado
        String jwt = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenResponse(jwt));
    }
}

