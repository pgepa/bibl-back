package br.gov.pa.pge.biblback.controller;

import br.gov.pa.pge.biblback.dto.UsuarioRequest;
import br.gov.pa.pge.biblback.dto.UsuarioResponse;
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
    public List<UsuarioResponse> listarTodos(@RequestParam(required = false) String termo) {
        if (termo != null && !termo.isBlank()) {
            return USUARIO_SERVICE.buscarPorTermo(termo).stream().map(UsuarioResponse::from).toList();
        }
        return USUARIO_SERVICE.listarTodos().stream().map(UsuarioResponse::from).toList();
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscarPorId(@PathVariable Long id) {
        return UsuarioResponse.from(USUARIO_SERVICE.buscarPorId(id));
    }

    @GetMapping("/nome/{nome}")
    public List<UsuarioResponse> buscarPorNome(@PathVariable String nome){
        return USUARIO_SERVICE.buscarPorNome(nome).stream().map(UsuarioResponse::from).toList();
    }

    @GetMapping("/cpf/{cpf}")
    public UsuarioResponse buscarPorCpf(@PathVariable String cpf){
        return UsuarioResponse.from(USUARIO_SERVICE.buscarPorCpf(cpf));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrar(@Valid @RequestBody UsuarioRequest request) {
        return UsuarioResponse.from(USUARIO_SERVICE.salvar(
                request.cpf(),
                request.nome(),
                request.email(),
                request.telefone(),
                request.matricula(),
                request.setor(),
                request.tipoUsuario(),
                request.senha()
        ));
    }

    @PutMapping("/{id}")
    public UsuarioResponse atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequest request) {
        return UsuarioResponse.from(USUARIO_SERVICE.atualizar(
                id,
                request.cpf(),
                request.nome(),
                request.email(),
                request.telefone(),
                request.matricula(),
                request.setor(),
                request.tipoUsuario(),
                request.senha()
        ));
    }

    @PatchMapping("desativar/{id}")
    public UsuarioResponse desativar(@PathVariable Long id) {
        return UsuarioResponse.from(USUARIO_SERVICE.desativar(id));
    }

    @PatchMapping("/ativar/{id}")
    public UsuarioResponse ativar(@PathVariable Long id){
        return UsuarioResponse.from(USUARIO_SERVICE.ativar(id));
    }
}
