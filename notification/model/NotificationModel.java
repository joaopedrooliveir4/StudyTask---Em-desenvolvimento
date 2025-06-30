package com.joaopedro.studytask.notification.model;

import com.joaopedro.studytask.model.StudyTaskModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notification")
public class NotificationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<String> mensagem;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    private Boolean visualizada = false;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private StudyTaskModel task;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getMensagem() {
        return mensagem;
    }

    public void setMensagem(List<String> mensagem) {
        this.mensagem = mensagem;
    }

    public Boolean getVisualizada() {
        return visualizada;
    }

    public void setVisualizada(Boolean visualizada) {
        this.visualizada = visualizada;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public StudyTaskModel getTask() {
        return task;
    }

    public void setTask(StudyTaskModel task) {
        this.task = task;
    }
}