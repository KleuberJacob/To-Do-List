package com.example.ToDo.List.controller;

import com.example.ToDo.List.dto.TarefaRequestDTO;
import com.example.ToDo.List.dto.TarefaResponseDTO;
import com.example.ToDo.List.service.TarefaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tarefas")
@RequiredArgsConstructor
public class tarefaController {

    private final TarefaService service;

    @PostMapping
    public List<TarefaResponseDTO> cadastrar(@Valid @RequestBody TarefaRequestDTO tarefaRequestDTO) {
        List<TarefaResponseDTO> cadastrar = service.cadastrar(tarefaRequestDTO);
        return cadastrar;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<List<TarefaResponseDTO>> checkTarefa(@PathVariable Long id) {
        List<TarefaResponseDTO> checkTarefa = service.checkTarefa(id);
        if (checkTarefa == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(checkTarefa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> atualizarTarefa(@Valid @PathVariable Long id,
                                                             @RequestBody TarefaRequestDTO tarefaRequestDTO) {
        TarefaResponseDTO atualizar = service.atualizar(id, tarefaRequestDTO);
        return ResponseEntity.ok(atualizar);
    }

    @GetMapping
    public List<TarefaResponseDTO> verTodas() {
        return service.verTodasTarefas();
    }
    @GetMapping("/realizadas")
    public ResponseEntity<List<TarefaResponseDTO>> verTodasRealizadas() {
        List<TarefaResponseDTO> tarefaResponseDTO = service.verTodasTarefasRealizadas();
        if (tarefaResponseDTO.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok(tarefaResponseDTO);
    }

    @GetMapping("/nao-realizadas")
    public ResponseEntity<List<TarefaResponseDTO>> verTodasNaoRealizadas() {
        List<TarefaResponseDTO> tarefaResponseDTO = service.verTodasTarefasNaoRealizadas();
        if (tarefaResponseDTO.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok(tarefaResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluirTarefa(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.ok().build();
    }

}
