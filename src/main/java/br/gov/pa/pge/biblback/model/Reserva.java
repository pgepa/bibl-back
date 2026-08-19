package br.gov.pa.pge.biblback.model;


import br.gov.pa.pge.biblback.enums.StatusReserva;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "reservas")
@NoArgsConstructor
@Getter
@Setter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "livro_id")
    private Livro livro;

    @Column(name = "data_emprestimo", nullable = false)
    private LocalDate localDate;

    @Column(name = "status_reserva", nullable = false)
    private StatusReserva statusReserva;


    public Reserva(Usuario usuario, Livro livro, LocalDate data){
        this.usuario = usuario;
        this.livro = livro;
        this.localDate = data;
    }
}
