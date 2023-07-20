package com.example.ToDo.List.repository;

import com.example.ToDo.List.domain.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query("FROM Tarefa t WHERE t.realizado IS NOT NULL ORDER BY t.criado ASC")
    List<Tarefa> buscarTodasTarefasRealizadas();

    @Query("FROM Tarefa t WHERE t.realizado IS NULL ORDER BY t.criado ASC")
    List<Tarefa> buscarTodasTarefasNaoRealizadas();

}
