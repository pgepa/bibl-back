package br.gov.pa.pge.biblback.model;

import br.gov.pa.pge.biblback.converter.StatusEmprestimoConverter;
import br.gov.pa.pge.biblback.enums.StatusEmprestimo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "emprestimos")
@NoArgsConstructor
@Getter
@Setter
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "data_emprestimo", nullable = false)
    private LocalDate dataEmprestimo;

    @Column(name = "data_prevista_devolucao", nullable = false)
    private LocalDate dataPrevistaDevolucao;

    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;

    @Convert(converter = StatusEmprestimoConverter.class)
    @Column(name = "status_emprestimo", nullable = false)
    private StatusEmprestimo statusEmprestimo;

    public Emprestimo(Livro livro, Usuario usuario, LocalDate dataEmprestimo,LocalDate dataPrevistaDevolucao, StatusEmprestimo statusEmprestimo) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.statusEmprestimo = statusEmprestimo;
    }
}
