package br.gov.pa.pge.biblback.model;

import br.gov.pa.pge.biblback.converter.StatusUsuarioConverter;
import br.gov.pa.pge.biblback.enums.StatusUsuario;
import br.gov.pa.pge.biblback.enums.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@Getter
@Setter
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @NotBlank
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @NotBlank
    @Column(name = "telefone", nullable = false, length = 11, unique = true)
    private String telefone;

    @Convert(converter = StatusUsuarioConverter.class)
    @Column(name = "status_usuario", nullable = false)
    private StatusUsuario statusUsuario;

    @Column(name = "tipo_usuario", nullable = false)
    private TipoUsuario tipoUsuario;

    public Usuario(String cpf,String nome, String email, String telefone, StatusUsuario statusUsuario, TipoUsuario tipoUsuario, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.statusUsuario = statusUsuario;
        this.tipoUsuario = tipoUsuario;
        this.senha = senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
    }

    @Override
    public @Nullable String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.statusUsuario == StatusUsuario.ATIVO;
    }
}
