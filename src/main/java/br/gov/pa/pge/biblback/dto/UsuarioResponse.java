package br.gov.pa.pge.biblback.dto;

import br.gov.pa.pge.biblback.enums.StatusUsuario;
import br.gov.pa.pge.biblback.enums.TipoUsuario;
import br.gov.pa.pge.biblback.model.Usuario;

public record UsuarioResponse(
        Long id,
        String cpf,
        String nome,
        String email,
        String telefone,
        StatusUsuario statusUsuario,
        TipoUsuario tipoUsuario
) {
    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getCpf(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getStatusUsuario(),
                usuario.getTipoUsuario()
        );
    }
}
