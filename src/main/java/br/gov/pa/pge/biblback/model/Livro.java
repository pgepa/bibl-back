package br.gov.pa.pge.biblback.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "livros")
@NoArgsConstructor
@Getter
@Setter
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotBlank
    @Column(name = "autor", nullable = false)
    private String autor;

    @NotBlank
    @Column(name = "isbn",nullable = false, unique = true)
    private String isbn;

    @NotNull
    @Column(name = "ano_lancamento", nullable = false, length = 4)
    private Integer anoLancamento;

    @Column(name = "is_disponivel", nullable = false)
    private Boolean disponivel = true;

    @Column(name = "registro")
    private String registro;

    @Column(name = "classificacao")
    private String classificacao;

    @Column(name = "tipo_documental")
    private String tipoDocumental;

    @Column(name = "local_publicacao")
    private String localPublicacao;

    @Column(name = "editora")
    private String editora;

    @Column(name = "edicao")
    private Integer edicao;

    @Column(name = "idioma")
    private String idioma;

    @Column(name = "paginas")
    private Integer paginas;

    @Column(name = "descritores", columnDefinition = "TEXT")
    private String descritores;

    public Livro(String titulo, String autor, String isbn, Integer anoLancamento) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.anoLancamento = anoLancamento;
        this.disponivel = true;
    }
}
