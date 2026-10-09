package br.gov.pa.pge.biblback.controller;


import br.gov.pa.pge.biblback.dto.RelatorioEmprestimoResponse;
import br.gov.pa.pge.biblback.service.RelatorioEmprestimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/relatorios")
@RequiredArgsConstructor
public class RelatorioEmprestimoController {

    private final RelatorioEmprestimoService relatorioEmprestimoService;

    // retorna dados estatisticos consolidados dos emprestimos
    @GetMapping("/emprestimos")
    public ResponseEntity<RelatorioEmprestimoResponse> gerarRelatorio() {
        return ResponseEntity.ok(relatorioEmprestimoService.gerarRelatorioEmprestimo());
    }

    // endpoint legado mantido para compatibilidade temporaria
    @GetMapping("/gerarRelatorio")
    public ResponseEntity<RelatorioEmprestimoResponse> gerarRelatorioLegado() {
        return ResponseEntity.ok(relatorioEmprestimoService.gerarRelatorioEmprestimo());
    }
}
