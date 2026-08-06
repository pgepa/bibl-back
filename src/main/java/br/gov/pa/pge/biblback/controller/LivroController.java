package br.gov.pa.pge.biblback.controller;

import br.gov.pa.pge.biblback.dto.LivroRequest;
import br.gov.pa.pge.biblback.dto.LivroResponse;
import br.gov.pa.pge.biblback.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @GetMapping
    public List<LivroResponse> listar() {
        return livroService.listarTodos().stream().map(LivroResponse::from).toList();
    }

    @GetMapping("/{id}")
    public LivroResponse buscar(@PathVariable Long id) {
        return LivroResponse.from(livroService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LivroResponse cadastrar(@Valid @RequestBody LivroRequest request) {
        return LivroResponse.from(livroService.cadastrar(
                request.titulo(),
                request.autor(),
                request.isbn(),
                request.anoLancamento()
        ));
    }

    @PutMapping("/{id}")
    public LivroResponse atualizar(@PathVariable Long id, @Valid @RequestBody LivroRequest request) {
        return LivroResponse.from(livroService.atualizar(
                id,
                request.titulo(),
                request.autor(),
                request.isbn(),
                request.anoLancamento()
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        livroService.deletar(id);
    }
}
