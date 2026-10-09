package br.gov.pa.pge.biblback.controller;


import br.gov.pa.pge.biblback.dto.emprestimo.EmprestimoRequest;
import br.gov.pa.pge.biblback.dto.emprestimo.EmprestimoResponse;
import br.gov.pa.pge.biblback.enums.StatusEmprestimo;
import br.gov.pa.pge.biblback.service.EmprestimoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emprestimos")
@RequiredArgsConstructor
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @GetMapping
    public ResponseEntity<List<EmprestimoResponse>> listarTodos() {
        return ResponseEntity.ok(emprestimoService.listarTodos().stream().map(EmprestimoResponse::from).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(EmprestimoResponse.from(emprestimoService.buscarPorId(id)));
    }

    @GetMapping("/status/{statusEmprestimo}")
    public ResponseEntity<List<EmprestimoResponse>> buscarPorStatus(@PathVariable StatusEmprestimo statusEmprestimo) {
        return ResponseEntity.ok(emprestimoService.buscarPorStatus(statusEmprestimo).stream().map(EmprestimoResponse::from).toList());
    }

    // cria novo emprestimo validando limites do usuario e disponibilidade do livro
    @PostMapping
    public ResponseEntity<EmprestimoResponse> realizar(@Valid @RequestBody EmprestimoRequest request) {
        EmprestimoResponse response = EmprestimoResponse.from(
                emprestimoService.realizarEmprestimo(request.livroId(), request.usuarioId(), request.dataPrevistaDevolucao())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // registra a devolucao do exemplar e torna o livro disponivel
    @PatchMapping("/{id}/devolver")
    public ResponseEntity<EmprestimoResponse> devolver(@PathVariable Long id) {
        return ResponseEntity.ok(EmprestimoResponse.from(emprestimoService.devolver(id)));
    }

    // renova o prazo de devolucao do emprestimo ativo em 7 dias
    @PatchMapping("/{id}/renovar")
    public ResponseEntity<EmprestimoResponse> renovarEmprestimo(@PathVariable Long id) {
        return ResponseEntity.ok(EmprestimoResponse.from(emprestimoService.renovarEmprestimo(id)));
    }
}
