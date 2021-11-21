package com.ostapdev.izosbotvk.service;

import com.ostapdev.izosbotvk.model.schedule.Day;
import com.ostapdev.izosbotvk.model.schedule.Lesson;
import com.ostapdev.izosbotvk.repo.GroupScheduleRepo;
import com.ostapdev.izosbotvk.repo.ScheduleConfigRepo;
import com.ostapdev.izosbotvk.sender.VkMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupScheduleService {

    private final GroupScheduleRepo groupScheduleRepo;
    private final ScheduleConfigRepo scheduleConfigRepo;
    private final StringBuilder messageBuilder = new StringBuilder();
    private final VkMessageSender messageSender;
    private final PeerConfigService peerConfigService;

    @Autowired
    public GroupScheduleService(GroupScheduleRepo groupScheduleRepo
            , ScheduleConfigRepo scheduleConfigRepo, VkMessageSender messageSender, PeerConfigService peerConfigService) {
        this.groupScheduleRepo = groupScheduleRepo;
        this.scheduleConfigRepo = scheduleConfigRepo;
        this.messageSender = messageSender;
        this.peerConfigService = peerConfigService;
    }

    public void getFullScheduleByGroup(Integer peerId,String group){
        if (peerConfigService.getCurrentPeerConfig(peerId).getGroupNumber().equals("")){
            messageSender
                    .send(peerId,"Группа беседы не установлена\nУстановить - !группа [номер]");
            return;
        }
        messageBuilder.setLength(0);
        if (groupScheduleRepo.getAllByGroup(group) != null){
            messageBuilder.append("Расписание на неделю для группы ")
                    .append(group).append("\n------------------------------------------------\n\n");
            groupScheduleRepo.getAllByGroup(group).getDays()
                    .forEach(day -> {
                        buildDaySchedule(day,true);
                    });
        }else {
            messageBuilder.append("Группы ").append(group).append(" не существует");
        }

        messageSender.send(peerId,messageBuilder.toString());
    }


    public void getDailyScheduleByGroup(Integer peerId,String group,boolean today){
        if (peerConfigService.getCurrentPeerConfig(peerId).getGroupNumber().equals("")){
            messageSender
                    .send(peerId,"Группа беседы не установлена\nУстановить - !группа [номер]");
            return;
        }
        messageBuilder.setLength(0);
        if (getCurrentWeekDay(today).equals("")){
            if (today) {
                messageSender.send(peerId,"Сегодня занятий нет");
            }
            else {
                messageSender.send(peerId,"Завтра занятий нет");
            }
            return;
        }
        if (groupScheduleRepo.getAllByGroup(group) != null){
            if (today) messageBuilder
                    .append("Расписание на сегодня для группы ");
            else messageBuilder
                    .append("Расписание на завтра для группы ");

            messageBuilder.append(group).append("\n------------------------------------------------\n\n");

            groupScheduleRepo.getAllByGroup(group).getDays()
                    .stream()
                    .filter(day -> day.getName().equals(getCurrentWeekDay(today)))
                    .findFirst()
                    .ifPresentOrElse(day -> {
                                if (day.getLessons().isEmpty()){
                                    messageBuilder.setLength(0);
                                    if (today) messageSender.send(peerId,"Сегодня занятий нет");
                                    else messageSender.send(peerId,"Завтра занятий нет");
                                }else {
                                    buildDaySchedule(day,false);
                                    messageSender.send(peerId,messageBuilder.toString());
                                }
                            }
                            ,()-> messageSender.send(peerId,"Сегодня занятий нет"));
        }else {
            messageSender.send(peerId,"Группы " + group + " не существует");
        }
    }

    private void buildDaySchedule(Day day,boolean isFullSchedule){
        messageBuilder.append("\n")
                .append(day.getName())
                .append("\n------------------------------------------------\n\n");

        day.getLessons()
                .stream()
                .sorted(Comparator.comparing(Lesson::getStartTime))
                .forEach(lesson -> buildLesson(lesson, !isFullSchedule));
    }

    private void buildLesson(Lesson lesson,boolean weekParityFilter){
        boolean weekType = false;
        String distance = "";
        String place = "";
        String teacher = "";
        String week = "";

        if (lesson.getWeek() !=null){
            if (!lesson.getWeek().equals("")){
                if (lesson.getWeek().equals("1")){
                    week = ", по нечетным неделям";
                }

                else if (lesson.getWeek().equals("2")){
                    week = ", по четным неделям";
                    weekType = true;
                }

                if (weekParityFilter && !weekType == scheduleConfigRepo.findAll().get(0).isEvenWeek()){
                    return;
                }

                else if (weekParityFilter && weekType == scheduleConfigRepo.findAll().get(0).isEvenWeek()){
                    week = "";
                }
            }
        }


        if (lesson.isDistance()){
            distance = "(дистанционно)";
        }

        if (lesson.getPlace() != null){
            if (!lesson.getPlace().equals("")){
                place = ", кабинет: " + lesson.getPlace();
            }
        }

        if (lesson.getTeacher() != null){
            if (!lesson.getTeacher().equals("")){
                teacher = ", преподаватель: " + lesson.getTeacher();
            }
        }

        messageBuilder.append(lesson.getStartTime()).append(": ")
                .append(lesson.getName()).append(" ").append(lesson.getType())
                .append(teacher).append(place).append(" ")
                .append(distance).append(week).append("\n");

    }

    private String getCurrentWeekDay(boolean today){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekDay;
        if (today) weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        else weekDay = calendar.get(Calendar.DAY_OF_WEEK) + 1;
        switch (weekDay){
            case 2:{
                return "Понедельник";
            }

            case 3:{
                return "Вторник";
            }

            case 4:{
                return "Среда";
            }

            case 5:{
                return "Четверг";
            }

            case 6:{
                return "Пятница";
            }

            case 7:{
                return "Суббота";
            }

            default:{
                return "";
            }
        }
    }
}
