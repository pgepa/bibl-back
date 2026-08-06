package br.gov.pa.pge.biblback.dto;

import br.gov.pa.pge.biblback.model.Usuario;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String telefone
) {
    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone()
        );
    }
}
