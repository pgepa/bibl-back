package br.gov.pa.pge.biblback.model;

import br.gov.pa.pge.biblback.converter.StatusUsuarioConverter;
import br.gov.pa.pge.biblback.enums.StatusUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@Getter
@Setter
public class Usuario {

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

    @NotBlank
    @Column(name = "telefone", nullable = false, length = 11, unique = true)
    private String telefone;

    @Convert(converter = StatusUsuarioConverter.class)
    @Column(name = "status_usuario", nullable = false)
    private StatusUsuario statusUsuario;

    public Usuario(String cpf,String nome, String email, String telefone, StatusUsuario statusUsuario) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.statusUsuario = statusUsuario;
    }
}
