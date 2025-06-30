package com.joaopedro.studytask.controller;

import com.joaopedro.studytask.dto.StudyTaskRequestDTO;
import com.joaopedro.studytask.dto.TotalDeTasksResponseDTO;
import com.joaopedro.studytask.enums.Prioridade;
import com.joaopedro.studytask.enums.Status;
import com.joaopedro.studytask.model.StudyTaskModel;
import com.joaopedro.studytask.notification.model.NotificationModel;
import com.joaopedro.studytask.service.StudyTaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("task")
public class StudyTaskController {

    @Autowired
    private StudyTaskService studyTaskService;

    @PostMapping
    public ResponseEntity<StudyTaskModel> enviarTarefa(@RequestBody @Valid StudyTaskRequestDTO dto, NotificationModel notification) {
        return ResponseEntity.ok(studyTaskService.enviarTarefa(dto, notification));
    }

    @GetMapping
    public List<StudyTaskModel> listarTarefas(StudyTaskModel task) {
        return studyTaskService.listarTarefas();
    }

    @GetMapping("prioridade/{prioridade}")
    public List<StudyTaskModel> retornarPorPrioridade(@PathVariable Prioridade prioridade) {
        return studyTaskService.filtrarPorPrioridade(prioridade);
    }

    @GetMapping("status/{status}")
    public List<StudyTaskModel> retornarPorStatus(@PathVariable Status status) {
        return studyTaskService.filtrarPorStatus(status);
    }

    @GetMapping("tema/{tema}")
    public List<StudyTaskModel> retornarPorTema(@PathVariable String tema) {
        return studyTaskService.filtrarPorTema(tema);
    }

    @GetMapping("data/{data}")
    public List<StudyTaskModel> retornarPorData(@PathVariable LocalDate data) {
        return studyTaskService.filtrarPorData(data);
    }

    @GetMapping("prioridades")
    public List<StudyTaskModel> ordenarPrioridades() {
        return studyTaskService.ordenarPrioridades();
    }

    @GetMapping("pages")
    public Page<StudyTaskModel> listarTarefasPaginadas(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return studyTaskService.listarTarefasPaginadas(pageable);
    }


    @GetMapping("ordenar/data/crescente")
    public List<StudyTaskModel> ordenarPorDatasAntigasRecentes() {
        return studyTaskService.ordenarDatasAntigasRecentes();
    }

    @GetMapping("ordenar/data/decrescente")
    public List<StudyTaskModel> ordenarPorDatasRecentesAntigas() {
        return studyTaskService.ordenarDatasRecentesAntigas();
    }

    @GetMapping("total")
    public TotalDeTasksResponseDTO totalDeTasks() {
        Long total = studyTaskService.totalDeTasks();
        String msg = "Número total de tarefas: " + total;
        return new TotalDeTasksResponseDTO(total, msg);
    }

    @PutMapping("atualizar/{id}")
    public StudyTaskModel atualizarTask(@PathVariable Long id, @RequestBody StudyTaskModel task) {
        return studyTaskService.atualizarTask(task, id);
    }

    @DeleteMapping("apagar/{id}")
    public void apagarTask(@PathVariable Long id) {
        studyTaskService.apagarTask(id);
    }
}
