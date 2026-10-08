package com.example.projetorotinaapi.service;

import com.example.projetorotinaapi.exception.TarefaNaoEncontradaException;
import com.example.projetorotinaapi.model.StatusTarefa;
import com.example.projetorotinaapi.model.Tarefa;
import com.example.projetorotinaapi.model.Usuario;
import com.example.projetorotinaapi.repository.TarefaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    @Scheduled(cron = "0 0 3 * * *")
    public void executarTarefaDiaria() {
        List<Tarefa> pendentes = tarefaRepository.findByStatus(StatusTarefa.PENDENTE);
        for (Tarefa tarefa : pendentes) {
            tarefa.setStatus(StatusTarefa.VENCIDA);
            tarefaRepository.save(tarefa);
        }

        List<Tarefa> concluidas = tarefaRepository.findByStatus(StatusTarefa.CONCLUIDA);
        for (Tarefa tarefa : concluidas) {
            tarefaRepository.deleteById(tarefa.getId());
        }
    }

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    public Tarefa buscarPorId(Long id, Usuario usuario) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNaoEncontradaException(id));

        if (!tarefa.getUsuario().getId().equals(usuario.getId())) {
            throw new TarefaNaoEncontradaException(id);
        }
        return tarefa;
    }

    public Tarefa atualizar(Long id, Tarefa dados, Usuario usuario) {
        Tarefa existente = buscarPorId(id, usuario);
        existente.setTitulo(dados.getTitulo());
        existente.setDescricao(dados.getDescricao());
        existente.setStatus(dados.getStatus());
        return tarefaRepository.save(existente);
    }

    public Tarefa salvar(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public void deletar(Long id, Usuario usuario) {
        Tarefa tarefa = buscarPorId(id, usuario);
        tarefaRepository.delete(tarefa);
    }

    public List<Tarefa> listarTodas(Usuario usuario) {
        return tarefaRepository.findByUsuario(usuario);
    }

    public Tarefa salvar(Tarefa tarefa, Usuario usuario) {
        tarefa.setUsuario(usuario);
        return tarefaRepository.save(tarefa);
    }
}
