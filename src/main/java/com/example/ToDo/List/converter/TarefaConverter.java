package com.example.ToDo.List.converter;

import com.example.ToDo.List.domain.Tarefa;
import com.example.ToDo.List.dto.TarefaRequestDTO;
import com.example.ToDo.List.dto.TarefaResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TarefaConverter {

    public Tarefa dtoToEntity(TarefaRequestDTO tarefaRequestDTO) {
        Tarefa entity = new Tarefa();
        entity.setNome(tarefaRequestDTO.getNome());
        entity.setDescricao(tarefaRequestDTO.getDescricao());
        entity.setPrioridade(tarefaRequestDTO.isPrioridade());
        entity.setCriado(LocalDateTime.now());
        return entity;
    }

    public TarefaResponseDTO entityToDto(Tarefa tarefa) {
        TarefaResponseDTO tarefaResponseDTO = new TarefaResponseDTO();
        tarefaResponseDTO.setId(tarefa.getId());
        tarefaResponseDTO.setNome(tarefa.getNome());
        tarefaResponseDTO.setDescricao(tarefa.getDescricao());
        tarefaResponseDTO.setPrioridade(tarefa.isPrioridade());
        tarefaResponseDTO.setCriado(tarefa.getCriado());
        tarefaResponseDTO.setRealizado(tarefa.getRealizado());
        return tarefaResponseDTO;
    }

}
