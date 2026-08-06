package br.gov.pa.pge.biblback.dto;

import java.time.Instant;

public record ErroResponse(
        Instant timestamp,
        int status,
        String erro,
        String mensagem,
        String caminho
) {
}
