package br.gov.pa.pge.biblback.controller;

import br.gov.pa.pge.biblback.dto.UsuarioRequest;
import br.gov.pa.pge.biblback.dto.UsuarioResponse;
import br.gov.pa.pge.biblback.model.Usuario;
import br.gov.pa.pge.biblback.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService USUARIO_SERVICE;

    @GetMapping
    public List<UsuarioResponse> listar() {
        return USUARIO_SERVICE.listarTodos().stream().map(UsuarioResponse::from).toList();
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscarPorId(@PathVariable Long id) {
        return UsuarioResponse.from(USUARIO_SERVICE.buscarPorId(id));
    }

    @GetMapping("/buscarPorNome")
    public List<UsuarioResponse> buscarPorNome(@RequestParam String nome){
        return USUARIO_SERVICE.buscarPorNome(nome).stream().map(UsuarioResponse::from).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrar(@Valid @RequestBody UsuarioRequest request) {
        return UsuarioResponse.from(USUARIO_SERVICE.salvar(
                request.nome(),
                request.email(),
                request.telefone()
        ));
    }

    @PutMapping("/{id}")
    public UsuarioResponse atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequest request) {
        return UsuarioResponse.from(USUARIO_SERVICE.atualizar(
                id,
                request.nome(),
                request.email(),
                request.telefone()
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        USUARIO_SERVICE.deletar(id);
    }
}
