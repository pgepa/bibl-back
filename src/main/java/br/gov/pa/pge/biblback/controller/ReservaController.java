package br.gov.pa.pge.biblback.controller;


import br.gov.pa.pge.biblback.dto.reserva.ReservaRequest;
import br.gov.pa.pge.biblback.dto.reserva.ReservaResponse;
import br.gov.pa.pge.biblback.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    // registra intencao de reserva quando exemplar estiver emprestado
    @PostMapping
    public ResponseEntity<ReservaResponse> fazerReserva(@Valid @RequestBody ReservaRequest reservaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ReservaResponse.from(reservaService.fazerReserva(reservaRequest)));
    }

    // lista todas as reservas cadastradas
    @GetMapping
    public ResponseEntity<List<ReservaResponse>> listarTodas() {
        return ResponseEntity.ok(reservaService.listarTodas()
                .stream()
                .map(ReservaResponse::from)
                .toList());
    }
}
