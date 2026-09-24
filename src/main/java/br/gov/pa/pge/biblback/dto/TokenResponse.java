package br.gov.pa.pge.biblback.dto;

import br.gov.pa.pge.biblback.enums.TipoUsuario;

public record TokenResponse(
        String token,
        Long id,
        String nome,
        String email,
        String matricula,
        String setor,
        TipoUsuario tipoUsuario
) {
    public TokenResponse(String token) {
        this(token, null, null, null, null, null, null);
    }
}
