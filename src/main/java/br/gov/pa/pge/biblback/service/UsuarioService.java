package br.gov.pa.pge.biblback.service;

import br.gov.pa.pge.biblback.enums.StatusUsuario;
import br.gov.pa.pge.biblback.exception.UsuarioJaAtivoException;
import br.gov.pa.pge.biblback.exception.UsuarioNaoAtivoException;
import br.gov.pa.pge.biblback.exception.UsuarioNaoEncontradoException;
import br.gov.pa.pge.biblback.model.Usuario;
import br.gov.pa.pge.biblback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import br.gov.pa.pge.biblback.enums.TipoUsuario;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository USUARIO_REPOSITORY;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(String cpf, String nome, String email, String telefone) {
        return salvar(cpf, nome, email, telefone, null, null, TipoUsuario.ROLE_USUARIO, null);
    }

    @Transactional
    public Usuario salvar(String cpf, String nome, String email, String telefone, String matricula, String setor, TipoUsuario tipoUsuario, String senha) {
        String encodedSenha = (senha != null && !senha.isBlank()) ? passwordEncoder.encode(senha) : null;
        TipoUsuario tipo = tipoUsuario != null ? tipoUsuario : TipoUsuario.ROLE_USUARIO;
        Usuario usuario = new Usuario(cpf, nome, email, telefone, StatusUsuario.ATIVO, tipo, encodedSenha);
        usuario.setMatricula(matricula);
        usuario.setSetor(setor);
        return USUARIO_REPOSITORY.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return USUARIO_REPOSITORY.findById(id).orElseThrow(UsuarioNaoEncontradoException::new);
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarPorNome(String nome){
        return USUARIO_REPOSITORY.findByNomeContainingIgnoreCase(nome.trim());
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorCpf(String cpf){
        return USUARIO_REPOSITORY.findByCpf(cpf.trim()).orElseThrow(UsuarioNaoEncontradoException::new);
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarPorTermo(String termo) {
        if (termo == null || termo.isBlank()) {
            return listarTodos();
        }
        return USUARIO_REPOSITORY.buscarPorTermo(termo.trim());
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return USUARIO_REPOSITORY.findAll();
    }

    @Transactional
    public Usuario atualizar(Long id, String cpf, String nome, String email, String telefone) {
        return atualizar(id, cpf, nome, email, telefone, null, null, null, null);
    }

    @Transactional
    public Usuario atualizar(Long id, String cpf, String nome, String email, String telefone,
                             String matricula, String setor, TipoUsuario tipoUsuario, String novaSenha) {
        Usuario usuario = buscarPorId(id);
        usuario.setCpf(cpf);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setTelefone(telefone);
        if (matricula != null) usuario.setMatricula(matricula);
        if (setor != null) usuario.setSetor(setor);
        if (tipoUsuario != null) usuario.setTipoUsuario(tipoUsuario);
        if (novaSenha != null && !novaSenha.isBlank()) {
            usuario.setSenha(passwordEncoder.encode(novaSenha));
        }
        return USUARIO_REPOSITORY.save(usuario);
    }

    @Transactional
    public Usuario desativar(Long id) {
        Usuario usuario = buscarPorId(id);
        if (usuario.getStatusUsuario() == StatusUsuario.INATIVO){
            throw new UsuarioNaoAtivoException("Este usuário já está desativado.");
        }
        usuario.setStatusUsuario(StatusUsuario.INATIVO);
        return USUARIO_REPOSITORY.save(usuario);
    }

    @Transactional
    public Usuario ativar(Long id){
        Usuario usuario = buscarPorId(id);
        if (usuario.getStatusUsuario() == StatusUsuario.ATIVO){
            throw new UsuarioJaAtivoException();
        }
        usuario.setStatusUsuario(StatusUsuario.ATIVO);
        return USUARIO_REPOSITORY.save(usuario);
    }
}
