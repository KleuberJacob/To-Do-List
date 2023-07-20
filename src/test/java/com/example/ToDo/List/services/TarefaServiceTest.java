package com.example.ToDo.List.services;

import com.example.ToDo.List.converter.TarefaConverter;
import com.example.ToDo.List.domain.Tarefa;
import com.example.ToDo.List.dto.TarefaRequestDTO;
import com.example.ToDo.List.dto.TarefaResponseDTO;
import com.example.ToDo.List.repository.TarefaRepository;
import com.example.ToDo.List.service.TarefaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DisplayName("Service de tarefas")
public class TarefaServiceTest {

    private TarefaRepository repository;
    private TarefaConverter converter;
    private TarefaService sut;

    @BeforeEach
    void inicializarMocks() {
        this.repository = Mockito.mock(TarefaRepository.class);
        this.converter = Mockito.mock(TarefaConverter.class);
        this.sut = new TarefaService(repository, converter);
    }

    @Test
    @DisplayName("Deve cadastrar uma nova tarefa com sucesso")
    void deveCadastrarUmaTarefa() {
        final var tarefaRequestDto = new TarefaRequestDTO();
        tarefaRequestDto.setNome("teste");
        final var tarefa = new Tarefa();
        Mockito.when(converter.dtoToEntity(tarefaRequestDto))
                .thenReturn(tarefa);

        sut.cadastrar(tarefaRequestDto);

        Mockito.verify(repository, Mockito.times(1))
                .save(tarefa);
    }

    @Test
    @DisplayName("Deve dar check na tarefa existente com sucesso")
    void deveDarCheckTarefaExistente() {
        final var idTarefa = 1L;
        final var tarefa = new Tarefa();
        tarefa.setRealizado(null);
        Mockito.when(repository.findById(idTarefa))
                        .thenReturn(Optional.of(tarefa));

        sut.checkTarefa(idTarefa);

        Mockito.verify(repository, Mockito.times(1))
                .save(tarefa);
    }

    @Test
    @DisplayName("Deve lançar RunTimeException ao validar existência da tarefa.")
    void deveLancarRunTimeExceptionAoValidarExistenciaTarefa() {
        final var idTarefa = 1L;
        Mockito.when(repository.findById(idTarefa))
                        .thenReturn(Optional.empty());

        final var exception = Assertions.assertThrows(RuntimeException.class,
                () -> sut.checkTarefa(idTarefa));

        Assertions.assertEquals("Tarefa informada não existe!", exception.getMessage());
        Mockito.verify(repository, Mockito.times(1))
                        .findById(idTarefa);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Deve retornar null quando tarefa já checada.")
    void deveRetornarNullQuandoTarefaChecada() {
        final var idTarefa = 1L;
        final var tarefa = new Tarefa();
        tarefa.setRealizado(LocalDateTime.now());
        Mockito.when(repository.findById(idTarefa))
                .thenReturn(Optional.of(tarefa));

        final var response = sut.checkTarefa(idTarefa);

        Assertions.assertEquals(null, response);
        Mockito.verify(repository, Mockito.times(1))
                .findById(idTarefa);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Deve retornar todas tarefas existentes.")
    void deveRetornarTodasTerefas() {
        final var tarefa = new Tarefa();
        final var listaTarefas = List.of(tarefa);
        final var tarefaResponseDto = new TarefaResponseDTO();
        Sort sort = Sort.by("prioridade").descending().and(
                Sort.by("criado").ascending()
        );
        Mockito.when(repository.findAll(sort))
                .thenReturn(listaTarefas);
        Mockito.when(converter.entityToDto(tarefa))
                .thenReturn(tarefaResponseDto);

        sut.verTodasTarefas();

        Mockito.verify(repository, Mockito.times(1))
                .findAll(sort);
        Mockito.verify(converter, Mockito.times(1))
                .entityToDto(tarefa);
    }

    @Test
    @DisplayName("Deve atualizar uma tarefa existente com sucesso.")
    void deveAtualizarUmaTarefaExistente() {
        final var idTarefa = 1L;
        final var tarefaRequestDto = new TarefaRequestDTO();
        final var tarefaResponseDto = new TarefaResponseDTO();
        final var tarefa = new Tarefa();
        tarefa.setNome("teste");
        Mockito.when(repository.findById(idTarefa))
                        .thenReturn(Optional.of(tarefa));
        Mockito.when(converter.entityToDto(tarefa))
                        .thenReturn(tarefaResponseDto);

        final var response = sut.atualizar(idTarefa, tarefaRequestDto);

        Assertions.assertEquals(tarefa.getNome(), response.getNome());
        Mockito.verify(repository, Mockito.times(1))
                .findById(idTarefa);
        Mockito.verify(repository, Mockito.times(1))
                .save(tarefa);
        Mockito.verify(converter, Mockito.times(1))
                .entityToDto(tarefa);
    }
    @Test
    @DisplayName("Deve lançar RunTimeException quando não encontrar a tarefa.")
    void deveLancarRunTimeExceptionQuandoNaoEncontrarTarefa() {
        final var idTarefa = 1L;
        final var tarefaRequestDto = new TarefaRequestDTO();
        Mockito.when(repository.findById(idTarefa))
                        .thenReturn(Optional.empty());

        final var exception = Assertions.assertThrows(RuntimeException.class,
                () -> sut.atualizar(idTarefa, tarefaRequestDto));

        Assertions.assertEquals("Tarefa informada não existe!", exception.getMessage());
        Mockito.verify(repository, Mockito.times(1))
                .findById(idTarefa);
        Mockito.verifyNoMoreInteractions(repository);
        Mockito.verifyNoInteractions(converter);
    }

}
