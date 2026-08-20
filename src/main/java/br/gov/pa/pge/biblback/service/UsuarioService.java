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

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository USUARIO_REPOSITORY;

    @Transactional
    public Usuario salvar(String cpf, String nome, String email, String telefone) {
        Usuario usuario = new Usuario(cpf, nome, email, telefone, StatusUsuario.ATIVO);
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
    public List<Usuario> listarTodos() {
        return USUARIO_REPOSITORY.findAll();
    }

    @Transactional
    public Usuario atualizar(Long id, String cpf, String nome, String email, String telefone) {
        Usuario usuario = buscarPorId(id);
        usuario.setCpf(cpf);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setTelefone(telefone);
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
