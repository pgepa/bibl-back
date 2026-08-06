package br.gov.pa.pge.biblback.controller;

import br.gov.pa.pge.biblback.dto.EmprestimoRequest;
import br.gov.pa.pge.biblback.dto.EmprestimoResponse;
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

    private final EmprestimoService emprestimoService;

    @GetMapping
    public List<EmprestimoResponse> listar() {
        return emprestimoService.listarTodos().stream().map(EmprestimoResponse::from).toList();
    }

    @GetMapping("/{id}")
    public EmprestimoResponse buscar(@PathVariable Long id) {
        return EmprestimoResponse.from(emprestimoService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmprestimoResponse realizar(@Valid @RequestBody EmprestimoRequest request) {
        return EmprestimoResponse.from(
                emprestimoService.realizarEmprestimo(request.livroId(), request.usuarioId())
        );
    }

    @PostMapping("/{id}/devolver")
    public EmprestimoResponse devolver(@PathVariable Long id) {
        return EmprestimoResponse.from(emprestimoService.devolver(id));
    }
}
