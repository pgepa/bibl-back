package br.gov.pa.pge.biblback.controller;


import br.gov.pa.pge.biblback.dto.RelatorioEmprestimoResponse;
import br.gov.pa.pge.biblback.service.RelatorioEmprestimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioEmprestimoController {
    private final RelatorioEmprestimoService RELATORIO_EMPRESTIMO_SERVICE;


    @GetMapping("/gerarRelatorio")
    public RelatorioEmprestimoResponse gerarRelatorio(){
        return RELATORIO_EMPRESTIMO_SERVICE.gerarRelatorioEmprestimo();
    }



}
