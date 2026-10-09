package br.gov.pa.pge.biblback.controller;


import br.gov.pa.pge.biblback.dto.livro.LivroRequest;
import br.gov.pa.pge.biblback.dto.livro.LivroResponse;
import br.gov.pa.pge.biblback.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    // lista livros com suporte a filtros opcionais por query parameters
    @GetMapping
    public ResponseEntity<List<LivroResponse>> listarTodos(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) Integer ano) {
        // busca por titulo caso informado
        if (titulo != null && !titulo.isBlank()) {
            return ResponseEntity.ok(livroService.buscarPorTitulo(titulo).stream().map(LivroResponse::from).toList());
        }
        // busca por autor caso informado
        if (autor != null && !autor.isBlank()) {
            return ResponseEntity.ok(livroService.buscarPorAutor(autor).stream().map(LivroResponse::from).toList());
        }
        // busca por ano de publicacao caso informado
        if (ano != null) {
            return ResponseEntity.ok(livroService.buscarPorAnoLancamento(ano).stream().map(LivroResponse::from).toList());
        }
        // retorna todos os livros caso nenhum filtro seja aplicado
        return ResponseEntity.ok(livroService.listarTodos().stream().map(LivroResponse::from).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(LivroResponse.from(livroService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<LivroResponse> cadastrar(@Valid @RequestBody LivroRequest request) {
        LivroResponse response = LivroResponse.from(livroService.cadastrar(
                request.titulo(),
                request.autor(),
                request.isbn(),
                request.anoLancamento()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponse> atualizar(@PathVariable Long id, @Valid @RequestBody LivroRequest request) {
        LivroResponse response = LivroResponse.from(livroService.atualizar(
                id,
                request.titulo(),
                request.autor(),
                request.isbn(),
                request.anoLancamento()
        ));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
