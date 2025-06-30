package com.joaopedro.studytask.model;

import com.joaopedro.studytask.enums.Prioridade;
import com.joaopedro.studytask.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class StudyTaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String tema;

    private String lembrete;

    @Column(name = "data_e_horario_de_inicio", nullable = false)
    private LocalDateTime dataEHorarioDeInicio;

    private LocalDate data = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer timer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getLembrete() {
        return lembrete;
    }

    public void setLembrete(String lembrete) {
        this.lembrete = lembrete;
    }

    public LocalDateTime getDataEHorarioDeInicio() {
        return dataEHorarioDeInicio;
    }

    public void setDataEHorarioDeInicio(LocalDateTime dataEHorarioDeInicio) {
        this.dataEHorarioDeInicio = dataEHorarioDeInicio;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getTimer() {
        return timer;
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
    }
}
