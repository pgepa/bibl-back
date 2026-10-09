package br.gov.pa.pge.biblback.service;

import br.gov.pa.pge.biblback.dto.usuario.UsuarioRequest;
import br.gov.pa.pge.biblback.dto.usuario.UsuarioResponse;
import br.gov.pa.pge.biblback.enums.StatusUsuario;
import br.gov.pa.pge.biblback.exception.UsuarioJaAtivoException;
import br.gov.pa.pge.biblback.exception.UsuarioNaoAtivoException;
import br.gov.pa.pge.biblback.exception.UsuarioNaoEncontradoException;
import br.gov.pa.pge.biblback.model.Usuario;
import br.gov.pa.pge.biblback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // cadastra novo usuario gerando hash seguro para a senha
    @Transactional
    public Usuario salvar(UsuarioRequest request) {
        // criptografa a senha antes de persistir no banco de dados
        String senhaCriptografada = passwordEncoder.encode(request.senha());
        Usuario usuario = new Usuario(
                request.cpf(),
                request.nome(),
                request.email(),
                request.telefone(),
                request.tipoUsuario(),
                senhaCriptografada
        );
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario salvar(String cpf, String nome, String email, String telefone) {
        Usuario usuario = new Usuario(cpf, nome, email, telefone, br.gov.pa.pge.biblback.enums.TipoUsuario.ROLE_USUARIO, null);
        return usuarioRepository.save(usuario);
    }

    // busca usuario e converte para dto de resposta
    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = buscarEntidadePorId(id);
        return UsuarioResponse.from(usuario);
    }

    // busca entidade usuario para uso interno por outros servicos
    @Transactional(readOnly = true)
    public Usuario buscarEntidadePorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(UsuarioNaoEncontradoException::new);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome.trim())
                .stream()
                .map(UsuarioResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorCpf(String cpf) {
        Usuario usuario = usuarioRepository.findByCpf(cpf.trim()).orElseThrow(UsuarioNaoEncontradoException::new);
        return UsuarioResponse.from(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll().stream().map(UsuarioResponse::from).toList();
    }

    @Transactional
    public Usuario atualizar(Long id, String cpf, String nome, String email, String telefone) {
        Usuario usuario = buscarEntidadePorId(id);
        usuario.setCpf(cpf);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setTelefone(telefone);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void desativar(Long id) {
        Usuario usuario = buscarEntidadePorId(id);
        if (usuario.getStatusUsuario() == StatusUsuario.INATIVO) {
            throw new UsuarioNaoAtivoException("Este usuário já está desativado.");
        }
        usuario.desativar();
    }

    @Transactional
    public void ativar(Long id) {
        Usuario usuario = buscarEntidadePorId(id);
        if (usuario.getStatusUsuario() == StatusUsuario.ATIVO) {
            throw new UsuarioJaAtivoException();
        }
        usuario.ativar();
    }
}
