package com.example.projetorotinaapi.controller;

import com.example.projetorotinaapi.model.Tarefa;
import com.example.projetorotinaapi.service.TarefaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tarefas", tarefaService.listarTodas());
        return "tarefas/lista";
    }

    @GetMapping("/nova")
    public String formularioNova(Model model) {
        model.addAttribute("tarefa", new Tarefa());
        return "tarefas/formulario";
    }

    @PostMapping
    public String salvar(@ModelAttribute Tarefa tarefa) {
        tarefaService.salvar(tarefa);
        return "redirect:/tarefas";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("tarefa", tarefaService.buscarPorId(id));
        return "tarefas/formulario";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        tarefaService.deletar(id);
        return "redirect:/tarefas";
    }
}
