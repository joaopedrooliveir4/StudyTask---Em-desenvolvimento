package com.joaopedro.studytask.repository;

import com.joaopedro.studytask.enums.Prioridade;
import com.joaopedro.studytask.enums.Status;
import com.joaopedro.studytask.model.StudyTaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StudyTaskRepository extends JpaRepository<StudyTaskModel, Long> {

    List<StudyTaskModel> findByPrioridade(Prioridade prioridade);

    List<StudyTaskModel> findByStatus(Status status);

    List<StudyTaskModel> findByTema(String tema);

    List<StudyTaskModel> findByData(LocalDate data);

    List<StudyTaskModel> findByPrioridadeInAndStatus(List<Prioridade> prioridades, Status status);

    boolean existsByDataEHorarioDeInicio(LocalDateTime dataEHorarioDeInicio);
}