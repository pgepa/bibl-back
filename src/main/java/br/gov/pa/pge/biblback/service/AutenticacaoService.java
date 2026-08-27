package br.gov.pa.pge.biblback.service;


import br.gov.pa.pge.biblback.exception.UsuarioNaoEncontradoException;
import br.gov.pa.pge.biblback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoService implements UserDetailsService {
    private final UsuarioRepository USUARIO_REPOSITORY;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return USUARIO_REPOSITORY.findByEmail(username).orElseThrow(UsuarioNaoEncontradoException::new);
    }
}
