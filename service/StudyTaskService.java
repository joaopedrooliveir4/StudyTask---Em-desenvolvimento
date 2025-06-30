package com.joaopedro.studytask.service;

import com.joaopedro.studytask.dto.StudyTaskRequestDTO;
import com.joaopedro.studytask.enums.Prioridade;
import com.joaopedro.studytask.enums.Status;
import com.joaopedro.studytask.exception.LegalArgumentException;
import com.joaopedro.studytask.model.StudyTaskModel;
import com.joaopedro.studytask.notification.service.NotificationService;
import com.joaopedro.studytask.repository.StudyTaskRepository;
import com.joaopedro.studytask.notification.model.NotificationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class StudyTaskService {

    @Autowired
    private StudyTaskRepository studyTaskRepository;

    @Autowired
    private NotificationService notificationService;

    public StudyTaskModel enviarTarefa(StudyTaskRequestDTO dto, NotificationModel notification) {
        LocalDateTime dataEHorarioDeInicio = dto.getDataEHorarioDeInicio();

        if (dataEHorarioDeInicio == null) {
            throw new IllegalArgumentException("O campo 'Data e horario de inicio' é obrigatório.");
        }

        if (dataEHorarioDeInicio.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("O horário de início da tarefa não pode ser no passado.");
        }

        boolean horarioOcupado = studyTaskRepository.existsByDataEHorarioDeInicio(dataEHorarioDeInicio);

        if (horarioOcupado) {
            throw new IllegalArgumentException("Opa! Já existe uma task agendada para esse horário");
        }

        StudyTaskModel task = new StudyTaskModel();
        task.setNome(dto.getNome());
        task.setTema(dto.getTema());
        task.setLembrete(dto.getLembrete());
        task.setDataEHorarioDeInicio(dto.getDataEHorarioDeInicio());
        task.setPrioridade(dto.getPrioridade());
        task.setTimer(dto.getTimer());
        task.setStatus(dto.getStatus());

        StudyTaskModel savedTask = studyTaskRepository.save(task);

        List<StudyTaskModel> tasksPendentesUrgentesEAltas = studyTaskRepository.findByPrioridadeInAndStatus(
                Arrays.asList(Prioridade.URGENTE, Prioridade.ALTA), Status.PENDENTE);

        notificationService.notificarTaskSalva(savedTask);
        notificationService.notificarUrgencia(savedTask);
        notificationService.notificarAlta(savedTask);
        notificationService.notificarTaskProxima(task);

        notificationService.notificarTasksUrgenteEAltaAleatoriamente(tasksPendentesUrgentesEAltas);

        return savedTask;
    }

    public List<StudyTaskModel> listarTarefas() {
        return studyTaskRepository.findAll();
    }

    public List<StudyTaskModel> filtrarPorPrioridade(Prioridade prioridade) {
        return studyTaskRepository.findByPrioridade(prioridade);
    }

    public List<StudyTaskModel> filtrarPorStatus(Status status) {
        return studyTaskRepository.findByStatus(status);
    }

    public List<StudyTaskModel> filtrarPorTema(String tema) {
        return studyTaskRepository.findByTema(tema);
    }

    public List<StudyTaskModel> filtrarPorData(LocalDate data) {
        return studyTaskRepository.findByData(data);
    }

    public List<StudyTaskModel> ordenarPrioridades() {
        List<StudyTaskModel> prioridades = studyTaskRepository.findAll();
        prioridades.sort(Comparator.comparing(StudyTaskModel::getPrioridade).reversed());

        return prioridades;
    }

    public Page<StudyTaskModel> listarTarefasPaginadas(Pageable pageable) {
        return studyTaskRepository.findAll(pageable);
    }

    public List<StudyTaskModel> ordenarDatasAntigasRecentes() {
        List<StudyTaskModel> datas = studyTaskRepository.findAll();
        datas.sort(Comparator.comparing(StudyTaskModel::getData));

        return datas;
    }

    public List<StudyTaskModel> ordenarDatasRecentesAntigas() {
        List<StudyTaskModel> datas = studyTaskRepository.findAll();
        datas.sort(Comparator.comparing(StudyTaskModel::getData).reversed());

        return datas;
    }

    public StudyTaskModel atualizarTask(StudyTaskModel task, Long id) {
        task.setId(id);
        return studyTaskRepository.save(task);
    }

    public void apagarTask(Long id) {
        studyTaskRepository.deleteById(id);
    }

    public Long totalDeTasks() {
        return studyTaskRepository.count();
    }
}