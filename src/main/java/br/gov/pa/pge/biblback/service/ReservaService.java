package br.gov.pa.pge.biblback.service;


import br.gov.pa.pge.biblback.dto.reserva.ReservaRequest;
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

    private final EmprestimoRepository emprestimoRepository;
    private final ReservaRepository reservaRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    // valida regras de elegibilidade e registra reserva para livro emprestado
    @Transactional
    public Reserva fazerReserva(ReservaRequest reservaRequest) {
        // garante que a data da reserva nao esteja no passado
        if (reservaRequest.data().isBefore(LocalDate.now())) {
            throw new DataReservaInvalidaException();
        }
        Usuario usuario = usuarioRepository
                .findById(reservaRequest.idUsuario())
                .orElseThrow(UsuarioNaoEncontradoException::new);

        // apenas usuarios com status ativo podem efetuar reservas
        if (usuario.getStatusUsuario() != StatusUsuario.ATIVO) {
            throw new UsuarioNaoAtivoException();
        }

        Livro livro = livroRepository
                .findById(reservaRequest.idLivro())
                .orElseThrow(LivroNaoEncontradoException::new);

        // livro disponivel deve ser emprestado diretamente sem reserva
        if (Boolean.TRUE.equals(livro.getDisponivel())) {
            throw new LivroDisponivelParaEmprestimoException();
        }

        // impede duplicidade de reserva em espera para o mesmo usuario e livro
        if (reservaRepository.existsByUsuarioAndLivroAndStatusReserva(usuario, livro, StatusReserva.AGUARDANDO)) {
            throw new JaPossuiReservaException();
        }

        // verifica se o usuario ja esta com exemplar deste livro emprestado no momento
        boolean usuarioJaPossuiLivro = emprestimoRepository.existsByUsuarioIdAndLivroIdAndStatusEmprestimo(
                usuario.getId(),
                livro.getId(),
                StatusEmprestimo.ATIVO
        );

        if (usuarioJaPossuiLivro) {
            throw new UsuarioJaPossuiLivroException();
        }

        // cria reserva com status inicial aguardando
        Reserva reserva = new Reserva(usuario, livro, reservaRequest.data());
        return reservaRepository.save(reserva);
    }

    @Transactional(readOnly = true)
    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }
}
