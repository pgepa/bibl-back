package br.gov.pa.pge.biblback.controller;


import br.gov.pa.pge.biblback.dto.ReservaRequest;
import br.gov.pa.pge.biblback.dto.ReservaResponse;
import br.gov.pa.pge.biblback.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService RESERVA_SERVICE;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaResponse fazerReserva(@Valid @RequestBody ReservaRequest reservaRequest){
        return ReservaResponse.from(RESERVA_SERVICE.fazerReserva(reservaRequest));
    }

    @GetMapping
    public List<ReservaResponse> listarTodas(){
        return RESERVA_SERVICE.listarTodas()
                .stream()
                .map(ReservaResponse::from)
                .toList();
    }
}
