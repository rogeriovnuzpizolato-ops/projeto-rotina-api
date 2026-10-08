package com.example.projetorotinaapi.controller;

import com.example.projetorotinaapi.model.Tarefa;
import com.example.projetorotinaapi.model.Usuario;
import com.example.projetorotinaapi.service.TarefaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public List<Tarefa> listar(@AuthenticationPrincipal Usuario usuario) {
        return tarefaService.listarTodas(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tarefa criar(
            @RequestBody Tarefa tarefa,
            @AuthenticationPrincipal Usuario usuario) {

        return tarefaService.salvar(tarefa, usuario);
    }

    @GetMapping("/{id}")
    public Tarefa buscarPorId(@PathVariable Long id) {
        return tarefaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Tarefa atualizar(
            @PathVariable Long id,
            @RequestBody Tarefa tarefa) {

        tarefa.setId(id);
        return tarefaService.salvar(tarefa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        tarefaService.deletar(id);
    }
}