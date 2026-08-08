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

    private final LivroService LIVRO_SERVICE;

    @GetMapping
    public List<LivroResponse> listarTodos() {
        return LIVRO_SERVICE.listarTodos().stream().map(LivroResponse::from).toList();
    }

    @GetMapping("/{id}")
    public LivroResponse buscarPorId(@PathVariable Long id) {
        return LivroResponse.from(LIVRO_SERVICE.buscarPorId(id));
    }

    @GetMapping("{/titulo}")
    public List<LivroResponse> buscarPorTitulo(@PathVariable String titulo) {
        return LIVRO_SERVICE.buscarPorTitulo(titulo).stream().map(LivroResponse::from).toList();
    }

    @GetMapping("{/autor}")
    public List<LivroResponse> buscarPorAutor(String autor){
        return LIVRO_SERVICE.buscarPorAutor(autor).stream().map(LivroResponse::from).toList();
    }

    @GetMapping("/{anoLancamento}")
    public List<LivroResponse> buscarPorAnoLancamento(@PathVariable Integer anoLancamento){
        return LIVRO_SERVICE.buscarPorAnoLancamento(anoLancamento).stream().map(LivroResponse::from).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LivroResponse cadastrar(@Valid @RequestBody LivroRequest request) {
        return LivroResponse.from(LIVRO_SERVICE.cadastrar(
                request.titulo(),
                request.autor(),
                request.isbn(),
                request.anoLancamento()
        ));
    }

    @PutMapping("/{id}")
    public LivroResponse atualizar(@PathVariable Long id, @Valid @RequestBody LivroRequest request) {
        return LivroResponse.from(LIVRO_SERVICE.atualizar(
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
        LIVRO_SERVICE.deletar(id);
    }
}
