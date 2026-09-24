package br.gov.pa.pge.biblback.controller;

import br.gov.pa.pge.biblback.dto.RelatorioEmprestimoResponse;
import br.gov.pa.pge.biblback.enums.StatusEmprestimo;
import br.gov.pa.pge.biblback.service.RelatorioEmprestimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
public class RelatorioEmprestimoController {
    private final RelatorioEmprestimoService RELATORIO_EMPRESTIMO_SERVICE;

    @GetMapping("/gerarRelatorio")
    public RelatorioEmprestimoResponse gerarRelatorio(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) StatusEmprestimo status) {
        return RELATORIO_EMPRESTIMO_SERVICE.gerarRelatorioEmprestimo(usuarioId, dataInicio, dataFim, status);
    }
}
