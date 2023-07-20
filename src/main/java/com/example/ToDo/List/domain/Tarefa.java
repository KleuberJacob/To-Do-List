package com.example.ToDo.List.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "criado")
    private LocalDateTime criado;
    @Column(name = "prioridade")
    private boolean prioridade;
    @Column(name = "realizado")
    private LocalDateTime realizado;

}
