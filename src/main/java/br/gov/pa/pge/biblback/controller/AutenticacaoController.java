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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager MANAGER;

    private final TokenService TOKEN_SERVICE;


    public ResponseEntity<TokenResponse> efetuarLogin(@Valid @RequestBody AutenticacaoRequest authRequest){

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.senha());

        Authentication authentication =  MANAGER.authenticate(authenticationToken);
        String JWT = TOKEN_SERVICE.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenResponse(JWT));
    }
}

