package br.gov.pa.pge.biblback.controller;

import br.gov.pa.pge.biblback.dto.usuario.UsuarioRequest;
import br.gov.pa.pge.biblback.dto.usuario.UsuarioResponse;
import br.gov.pa.pge.biblback.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok().body(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(usuarioService.buscarPorId(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<UsuarioResponse>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok().body(usuarioService.buscarPorNome(nome));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<UsuarioResponse> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok().body(usuarioService.buscarPorCpf(cpf));
    }

    // cadastra novo usuario e retorna 201 created
    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponse.from(usuarioService.salvar(request)));
    }

    // atualiza informacoes cadastrais do usuario
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok().body(UsuarioResponse.from(usuarioService.atualizar(
                id,
                request.cpf(),
                request.nome(),
                request.email(),
                request.telefone()
        )));
    }

    // inativa o usuario no sistema sem excluir o registro fisico
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        usuarioService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    // reativa o acesso do usuario no sistema
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        usuarioService.ativar(id);
        return ResponseEntity.noContent().build();
    }
}
