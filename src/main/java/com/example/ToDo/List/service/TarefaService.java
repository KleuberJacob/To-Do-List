package com.example.ToDo.List.service;

import com.example.ToDo.List.converter.TarefaConverter;
import com.example.ToDo.List.domain.Tarefa;
import com.example.ToDo.List.dto.TarefaRequestDTO;
import com.example.ToDo.List.dto.TarefaResponseDTO;
import com.example.ToDo.List.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository repository;
    private final TarefaConverter converter;

    public List<TarefaResponseDTO> cadastrar(TarefaRequestDTO tarefaRequestDTO) {
        repository.save(converter.dtoToEntity(tarefaRequestDTO));
        return verTodasTarefas();
    }

    public List<TarefaResponseDTO> checkTarefa(Long id) {
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa informada não existe!"));
        if (tarefa.getRealizado() != null) return null;
        tarefa.setRealizado(LocalDateTime.now());
        repository.save(tarefa);
        return verTodasTarefas();
    }

    public List<TarefaResponseDTO> verTodasTarefas() {
        Sort sort = Sort.by("prioridade").descending().and(
                Sort.by("criado").ascending()
        );
        List<Tarefa> tarefas = repository.findAll(sort);
        List<TarefaResponseDTO> tarefasDto = new ArrayList<>();
        for (Tarefa tarefa: tarefas) {
            TarefaResponseDTO tarefaResponseDTO = converter.entityToDto(tarefa);
            tarefasDto.add(tarefaResponseDTO);
        }
        return tarefasDto;
    }

    public List<TarefaResponseDTO> verTodasTarefasRealizadas() {
        List<Tarefa> tarefasRealizadas = repository.buscarTodasTarefasRealizadas();
        List<TarefaResponseDTO> tarefasDto = new ArrayList<>();
        for (Tarefa tarefa: tarefasRealizadas) {
            TarefaResponseDTO tarefaResponseDTO = converter.entityToDto(tarefa);
            tarefasDto.add(tarefaResponseDTO);
        }
        return tarefasDto;
    }

    public List<TarefaResponseDTO> verTodasTarefasNaoRealizadas() {
        List<Tarefa> tarefasRealizadas = repository.buscarTodasTarefasNaoRealizadas();
        List<TarefaResponseDTO> tarefasDto = new ArrayList<>();
        for (Tarefa tarefa: tarefasRealizadas) {
            TarefaResponseDTO tarefaResponseDTO = converter.entityToDto(tarefa);
            tarefasDto.add(tarefaResponseDTO);
        }
        return tarefasDto;
    }

    public TarefaResponseDTO atualizar(Long id, TarefaRequestDTO tarefaRequestDTO) {
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa informada não existe!"));

        tarefa.setNome(tarefaRequestDTO.getNome());
        tarefa.setDescricao(tarefaRequestDTO.getDescricao());
        tarefa.setPrioridade(tarefaRequestDTO.isPrioridade());
        tarefa.setCriado(LocalDateTime.now());
        tarefa.setRealizado(null);
        repository.save(tarefa);
        return converter.entityToDto(tarefa);
    }

    public void excluir(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa informada não existe!"));
        repository.deleteById(id);
    }

}
