package com.example.ToDo.List.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarefaRequestDTO {

    @NotBlank(message = "O campo nome deve ser preenchido obrigatoriamente.")
    private String nome;
    @NotBlank(message = "O campo descrição deve ser preenchido obrigatoriamente.")
    private String descricao;
    private boolean prioridade;

}
