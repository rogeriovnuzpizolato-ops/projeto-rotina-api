package com.example.projetorotinaapi.service;

import com.example.projetorotinaapi.model.StatusTarefa;
import com.example.projetorotinaapi.model.Tarefa;
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

    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com id: " + id));
    }

    public Tarefa salvar(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public void deletar(Long id) {
        tarefaRepository.deleteById(id);
    }
}
