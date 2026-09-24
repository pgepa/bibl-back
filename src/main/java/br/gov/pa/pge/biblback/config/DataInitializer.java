package br.gov.pa.pge.biblback.config;

import br.gov.pa.pge.biblback.enums.StatusUsuario;
import br.gov.pa.pge.biblback.enums.TipoUsuario;
import br.gov.pa.pge.biblback.model.Usuario;
import br.gov.pa.pge.biblback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        usuarioRepository.findByEmail("admin@bibl.gov.br").ifPresentOrElse(
                admin -> {},
                () -> {
                    Usuario admin = new Usuario(
                            "00000000000",
                            "Administrador",
                            "admin@bibl.gov.br",
                            "91999990000",
                            StatusUsuario.ATIVO,
                            TipoUsuario.ROLE_ADMIN,
                            passwordEncoder.encode("123456")
                    );
                    admin.setMatricula("ADM-001");
                    admin.setSetor("Biblioteca Central");
                    usuarioRepository.save(admin);
                }
        );

        usuarioRepository.findByEmail("victor@gmail.com").ifPresent(user -> {
            user.setTipoUsuario(TipoUsuario.ROLE_ADMIN);
            if (user.getSenha() == null || user.getSenha().isBlank()) {
                user.setSenha(passwordEncoder.encode("123456"));
            }
            if (user.getMatricula() == null) {
                user.setMatricula("PGE-1001");
            }
            if (user.getSetor() == null) {
                user.setSetor("ESAP");
            }
            usuarioRepository.save(user);
        });
    }
}
