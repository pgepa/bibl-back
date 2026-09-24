package br.gov.pa.pge.biblback.service;


import br.gov.pa.pge.biblback.dto.ReservaRequest;
import br.gov.pa.pge.biblback.enums.StatusEmprestimo;
import br.gov.pa.pge.biblback.enums.StatusReserva;
import br.gov.pa.pge.biblback.enums.StatusUsuario;
import br.gov.pa.pge.biblback.exception.*;
import br.gov.pa.pge.biblback.model.Livro;
import br.gov.pa.pge.biblback.model.Reserva;
import br.gov.pa.pge.biblback.model.Usuario;
import br.gov.pa.pge.biblback.repository.EmprestimoRepository;
import br.gov.pa.pge.biblback.repository.LivroRepository;
import br.gov.pa.pge.biblback.repository.ReservaRepository;
import br.gov.pa.pge.biblback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final EmprestimoRepository EMPRESTIMO_REPOSITORY;
    private final ReservaRepository RESERVA_REPOSITORY;
    private final LivroRepository LIVRO_REPOSITORY;
    private final UsuarioRepository USUARIO_REPOSITORY;


    @Transactional
    public Reserva fazerReserva(ReservaRequest reservaRequest) {

        if (reservaRequest.data().isBefore(LocalDate.now())) {
            throw new DataReservaInvalidaException();
        }
        Usuario usuario = USUARIO_REPOSITORY
                .findById(reservaRequest.idUsuario())
                .orElseThrow(UsuarioNaoEncontradoException::new);

        if (usuario.getStatusUsuario() != StatusUsuario.ATIVO) {
            throw new UsuarioNaoAtivoException();
        }

        Livro livro = LIVRO_REPOSITORY
                .findById(reservaRequest.idLivro())
                .orElseThrow(LivroNaoEncontradoException::new);

        if (Boolean.TRUE.equals(livro.getDisponivel())) {
            throw new IllegalArgumentException("Este livro já está disponível para empréstimo e não necessita de reserva.");
        }

        if (RESERVA_REPOSITORY.existsByUsuarioAndLivroAndStatusReserva(usuario, livro, StatusReserva.AGUARDANDO)) {
            throw new JaPossuiReservaException();
        }

        boolean usuarioJaPossuiLivro = EMPRESTIMO_REPOSITORY.existsByUsuarioIdAndLivroIdAndStatusEmprestimo(usuario.getId(),
                livro.getId(), StatusEmprestimo.ATIVO);

        if (usuarioJaPossuiLivro) {
            throw new UsuarioJaPossuiLivroException();
        }

        Reserva reserva = new Reserva(usuario, livro, LocalDate.now());
        reserva.setStatusReserva(StatusReserva.AGUARDANDO);
        return RESERVA_REPOSITORY.save(reserva);
    }

    @Transactional(readOnly = true)
    public List<Reserva> listarTodas() {
        return RESERVA_REPOSITORY.findAll();
    }
}
