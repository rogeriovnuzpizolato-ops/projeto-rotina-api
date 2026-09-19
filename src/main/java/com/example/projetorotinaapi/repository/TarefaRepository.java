package com.example.projetorotinaapi.repository;

import com.example.projetorotinaapi.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
