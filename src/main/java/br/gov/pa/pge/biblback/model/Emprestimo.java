package br.gov.pa.pge.biblback.model;

import br.gov.pa.pge.biblback.enums.StatusEmprestimo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "emprestimo")
@NoArgsConstructor
@Getter
@Setter
public class Emprestimo {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @Column(name = "nome_leitor", nullable = false )
    private String nomeLeitor;


    @Column(name = "data_emprestismo", nullable = false)
    private LocalDate dataEmprestimo;

    @Column(name = "data_devolucao", nullable = false)
    private LocalDate dataDevolucao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEmprestimo statusEmprestimo;

    public Emprestimo(Livro livro, String nomeLeitor, LocalDate dataEmprestimo, LocalDate dataDevolucao, StatusEmprestimo statusEmprestimo) {
        this.livro = livro;
        this.nomeLeitor = nomeLeitor;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.statusEmprestimo = statusEmprestimo;
    }
}
