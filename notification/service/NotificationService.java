package com.joaopedro.studytask.notification.service;

import com.joaopedro.studytask.enums.Prioridade;
import com.joaopedro.studytask.model.StudyTaskModel;
import com.joaopedro.studytask.notification.model.NotificationModel;
import com.joaopedro.studytask.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // Constante pra usar no alerta de task próxima
    private final long ALERT_BEFORE_MINS = 15;

    public void notificarTaskSalva(StudyTaskModel task) {
        NotificationModel noti = new NotificationModel();
        noti.setMensagem(Arrays.asList("✅ Task salva com sucesso!"));
        noti.setTask(task);
        notificationRepository.save(noti);
    }

    public void notificarUrgencia(StudyTaskModel task) {
        if (task.getPrioridade().equals(Prioridade.URGENTE)) {
            NotificationModel noti = new NotificationModel();
            noti.setMensagem(Arrays.asList("⚠️ Atenção: Você tem tasks de prioridade URGENTE. Se organize!"));
            noti.setTask(task);
            notificationRepository.save(noti);
        }
    }

    public void notificarAlta(StudyTaskModel task) {
        if (task.getPrioridade().equals(Prioridade.ALTA)) {
            NotificationModel noti = new NotificationModel();
            noti.setMensagem(Arrays.asList("⚠️ Atenção: Você tem tasks de prioridade ALTA. Se organize!"));
            noti.setTask(task);
            notificationRepository.save(noti);
        }
    }

    public void notificarTaskProxima(StudyTaskModel task) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicio = task.getDataEHorarioDeInicio();

        LocalDateTime janelaInicial = agora.plusMinutes(ALERT_BEFORE_MINS - 1);
        LocalDateTime janelaFinal = agora.plusMinutes(ALERT_BEFORE_MINS);

        if (!inicio.isBefore(janelaInicial) && !inicio.isAfter(janelaFinal)) {
            NotificationModel noti = new NotificationModel();
            noti.setMensagem(Arrays.asList("⏰ Atenção: Você tem uma task que começa em 15 minutos!"));
            noti.setTask(task);
            notificationRepository.save(noti);
        }
    }

    public void notificarTasksUrgenteEAltaAleatoriamente(List<StudyTaskModel> tasks) {
        System.out.println("Quantidade total de tasks recebidas: " + (tasks == null ? 0 : tasks.size()));

        if (tasks == null || tasks.isEmpty()) {
            System.out.println("Nenhuma task para notificar.");
            return;
        }

        List<StudyTaskModel> tasksFiltradas = tasks.stream()
                .filter(t -> t.getPrioridade() == Prioridade.URGENTE || t.getPrioridade() == Prioridade.ALTA)
                .toList();

        System.out.println("Tasks URGENTE ou ALTA para notificar: " + tasksFiltradas.size());

        if (tasksFiltradas.isEmpty()) {
            System.out.println("Nenhuma task com prioridade URGENTE ou ALTA para notificar.");
            return;
        }

        Collections.shuffle(tasksFiltradas);

        System.out.println("Ordem e delays das notificações:");

        int delaySegundos = 0;
        for (StudyTaskModel task : tasksFiltradas) {
            delaySegundos += 5 + (int)(Math.random() * 10);

            LocalDateTime horarioNotificacao = LocalDateTime.now().plusSeconds(delaySegundos);

            System.out.println("Task " + task.getId() + " com prioridade " + task.getPrioridade() +
                    " vai ser notificada em " + horarioNotificacao);

            int delayFinal = delaySegundos;
            scheduler.schedule(() -> {
                if (task.getPrioridade() == Prioridade.URGENTE) {
                    notificarUrgencia(task);
                } else {
                    notificarAlta(task);
                }
            }, delayFinal, TimeUnit.SECONDS);
        }
    }
}