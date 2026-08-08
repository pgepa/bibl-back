package br.gov.pa.pge.biblback.service;

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
    public Usuario salvar(String nome, String email, String telefone) {
        Usuario usuario = new Usuario(nome, email, telefone);
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
    public List<Usuario> listarTodos() {
        return USUARIO_REPOSITORY.findAll();
    }

    @Transactional
    public Usuario atualizar(Long id, String nome, String email, String telefone) {
        Usuario usuario = buscarPorId(id);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setTelefone(telefone);
        return USUARIO_REPOSITORY.save(usuario);
    }

    @Transactional
    public void deletar(Long id) {
        Usuario usuario = buscarPorId(id);
        USUARIO_REPOSITORY.delete(usuario);
    }
}
