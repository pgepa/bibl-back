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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @Column(name = "data_reserva", nullable = false)
    private LocalDate dataReserva;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_reserva", nullable = false)
    private StatusReserva statusReserva = StatusReserva.AGUARDANDO;

    public Reserva(Usuario usuario, Livro livro, LocalDate dataReserva) {
        this.usuario = usuario;
        this.livro = livro;
        this.dataReserva = dataReserva;
    }

    public LocalDate getLocalDate() {
        return this.dataReserva;
    }
}
