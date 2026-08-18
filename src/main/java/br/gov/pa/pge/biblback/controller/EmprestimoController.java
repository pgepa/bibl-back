package br.gov.pa.pge.biblback.controller;

import br.gov.pa.pge.biblback.dto.EmprestimoRequest;
import br.gov.pa.pge.biblback.dto.EmprestimoResponse;
import br.gov.pa.pge.biblback.enums.StatusEmprestimo;
import br.gov.pa.pge.biblback.service.EmprestimoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprestimos")
@RequiredArgsConstructor
public class EmprestimoController {

    private final EmprestimoService EMPRESTIMO_SERVICE;

    @GetMapping
    public List<EmprestimoResponse> listarTodos() {
        return EMPRESTIMO_SERVICE.listarTodos().stream().map(EmprestimoResponse::from).toList();
    }

    @GetMapping("/{id}")
    public EmprestimoResponse buscar(@PathVariable Long id) {
        return EmprestimoResponse.from(EMPRESTIMO_SERVICE.buscarPorId(id));
    }

    @GetMapping("/status/{statusEmprestimo}")
    public List<EmprestimoResponse> buscarPorStatus(@PathVariable StatusEmprestimo statusEmprestimo) {
        return EMPRESTIMO_SERVICE.buscarPorStatus(statusEmprestimo).stream().map(EmprestimoResponse::from).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmprestimoResponse realizar(@Valid @RequestBody EmprestimoRequest request) {
        return EmprestimoResponse.from(
                EMPRESTIMO_SERVICE.realizarEmprestimo(request.livroId(), request.usuarioId(), request.dataPrevistaDevolucao())
        );
    }

    @PatchMapping("/{id}/devolver")
    public EmprestimoResponse devolver(@PathVariable Long id) {
        return EmprestimoResponse.from(EMPRESTIMO_SERVICE.devolver(id));
    }

    @PatchMapping("/{id}/renovar")
    public EmprestimoResponse renovarEmprestimo(@PathVariable Long id) {
        return EmprestimoResponse.from(EMPRESTIMO_SERVICE.renovarEmprestimo(id));
    }
}
