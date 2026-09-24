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
@RequestMapping({"/login", "/api/login"})
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager MANAGER;

    private final TokenService TOKEN_SERVICE;

    @PostMapping
    public ResponseEntity<TokenResponse> efetuarLogin(@Valid @RequestBody AutenticacaoRequest authRequest){

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.senha());

        Authentication authentication =  MANAGER.authenticate(authenticationToken);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        String JWT = TOKEN_SERVICE.gerarToken(usuario);

        return ResponseEntity.ok(new TokenResponse(
                JWT,
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getMatricula(),
                usuario.getSetor(),
                usuario.getTipoUsuario()
        ));
    }
}

