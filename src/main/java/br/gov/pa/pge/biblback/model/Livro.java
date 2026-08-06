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
    @Column(nullable = false)
    private String titulo;

    @NotBlank
    @Column(nullable = false)
    private String autor;

    @NotBlank
    @Column(nullable = false)
    private String isbn;

    @NotNull
    @Column(name = "ano_lancamento", nullable = false)
    private Integer anoLancamento;

    @Column(nullable = false)
    private Boolean disponivel = true;

    public Livro(String titulo, String autor, String isbn, Integer anoLancamento) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.anoLancamento = anoLancamento;
        this.disponivel = true;
    }
}
