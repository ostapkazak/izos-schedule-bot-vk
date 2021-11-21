package com.ostapdev.izosbotvk.service;

import com.ostapdev.izosbotvk.model.exam.Exam;
import com.ostapdev.izosbotvk.repo.GroupExamRepo;
import com.ostapdev.izosbotvk.sender.VkMessageSender;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class GroupExamService {
    private final GroupExamRepo groupExamRepo;
    private final StringBuilder messageBuilder = new StringBuilder();
    private final VkMessageSender messageSender;
    private final PeerConfigService peerConfigService;

    @Autowired
    public GroupExamService(GroupExamRepo groupExamRepo, VkMessageSender messageSender, PeerConfigService peerConfigService) {
        this.groupExamRepo = groupExamRepo;
        this.messageSender = messageSender;
        this.peerConfigService = peerConfigService;
    }

    public void getAllExamsByGroup(Integer peerId,String group){
        if (peerConfigService.getCurrentPeerConfig(peerId).getGroupNumber().equals("")){
            messageSender
                    .send(peerId,"Группа беседы не установлена\nУстановить - !группа [номер]");
            return;
        }
        messageBuilder.setLength(0);
        messageBuilder.append("Список экзаменов для группы ")
                .append(group).append("\n------------------------------------------------\n\n");
        if (groupExamRepo.findByGroup(group).isEmpty()){
            messageSender.send(peerId,"Группы " + group + " не существует");
        }

        groupExamRepo.findByGroup(group).get().getExams().stream()
                .sorted(Comparator.comparing(Exam::getStartAt))
                .forEach(exam -> messageBuilder
                        .append(exam.getName()).append("\n\n")
                        .append("Время начала: ")
                        .append(exam.getStartAt()
                                .toString(DateTimeFormat.forPattern("dd.MM HH:mm").withZone(DateTimeZone.UTC)))
                        .append("\nПреподаватель: ")
                        .append(exam.getTeacher()).append("\nМесто проведения: ")
                        .append(exam.getPlace()).append("\n------------------------------------------------\n\n"));
        messageSender.send(peerId,messageBuilder.toString());
    }

    public boolean isTodayHasExam(String group,int day){
        if (groupExamRepo.findByGroup(group).isEmpty()){
            return false;
        }

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        groupExamRepo.findByGroup(group).get().getExams().stream()
                .filter(exam -> exam.getStartAt().getDayOfMonth() == day)
                .findFirst()
                .ifPresentOrElse(exam -> atomicBoolean.set(true),()-> {});
        return atomicBoolean.get();
    }

    public void todayExamMessage(Integer peerId,String group,int day){
        getTodayExams(group,day);
        messageSender.send(peerId,messageBuilder.toString());
    }

    private void getTodayExams(String group,int day){
        if (groupExamRepo.findByGroup(group).isEmpty()){
            return;
        }
        messageBuilder.setLength(0);
        messageBuilder.append("Сегодняшний экзамен для группы ")
                .append(group)
                .append("\n------------------------------------------------\n\n");

        groupExamRepo.findByGroup(group).get().getExams().stream()
                .filter(exam -> exam.getStartAt().getDayOfMonth() == day)
                .findFirst()
                .ifPresentOrElse(exam -> messageBuilder
                        .append(exam.getName())
                        .append("\n\n").append("Время начала: ")
                        .append(exam.getStartAt()
                                .toString(DateTimeFormat.forPattern("dd.MM HH:mm").withZone(DateTimeZone.UTC)))
                        .append("\nПреподаватель: ").append(exam.getTeacher())
                        .append("\nМесто проведения: ").append(exam.getPlace()),()-> messageBuilder.setLength(0));
    }
}
