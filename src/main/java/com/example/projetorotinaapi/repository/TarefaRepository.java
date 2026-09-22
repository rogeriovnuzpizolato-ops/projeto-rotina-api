package com.example.projetorotinaapi.repository;

import com.example.projetorotinaapi.model.StatusTarefa;
import com.example.projetorotinaapi.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByStatus(StatusTarefa status);
}
