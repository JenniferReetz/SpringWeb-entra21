package br.com.senac.projeto_spring_aula.todolist.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "lista")
@Getter
@Setter
public class ListaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;
    // SALVAR COMO STRING NO BANCO (TO_DO, DONE, CANCELLED)
    // é uma forma mais lenta mas legível
    // SALVAR COMO ORDINAL (1, 2, 3)
    // É mais rápido de fazer query, mas precisa saber qual é qual
    @Enumerated(EnumType.STRING)
    private ListaStatus status;
}
