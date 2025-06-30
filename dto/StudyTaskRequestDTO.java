package com.joaopedro.studytask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joaopedro.studytask.enums.Prioridade;
import com.joaopedro.studytask.enums.Status;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class StudyTaskRequestDTO {

    @NotBlank(message = "O campo Nome é obrigatório")
    @Size(max = 100)
    private String nome;

    @NotBlank(message = "O campo Tema é obrigatório")
    @Size(max = 50)
    private String tema;

    @Size(max = 255)
    @NotBlank(message = "O campo Lembrete é obrigatório")
    private String lembrete;

    @JsonProperty("dataEHorarioDeInicio")
    private LocalDateTime dataEHorarioDeInicio;

    @NotNull(message = "O campo Prioridade é obrigatório")
    private Prioridade prioridade;

    @NotNull(message = "O campo Status é obrigatório")
    private Status status;

    private Integer timer;

    public StudyTaskRequestDTO() {}

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

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

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
