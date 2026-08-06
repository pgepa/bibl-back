package br.gov.pa.pge.biblback.service;


import br.gov.pa.pge.biblback.exception.UsuarioNaoEncontradoException;
import br.gov.pa.pge.biblback.model.Usuario;
import br.gov.pa.pge.biblback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository USUARIO_REPOSITORY;

    public Usuario salvar(String nome, String email, String telefone){


        Usuario usuario = new Usuario(nome, email, telefone);
        return USUARIO_REPOSITORY.save(usuario);
    }

    public Usuario buscarPorId(Long id){

        return USUARIO_REPOSITORY.findById(id).orElseThrow(UsuarioNaoEncontradoException::new);
    }

    public List<Usuario> listarTodos(){
        return USUARIO_REPOSITORY.findAll();
    }
}
