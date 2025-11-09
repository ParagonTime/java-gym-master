package ru.yandex.practicum.gym;

import java.util.*;
import java.util.stream.Collectors;

public class Timetable {

    private final Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timetable;
    private final Map<Coach, Integer> coachWorkload;

    public Timetable() {
        timetable = new HashMap<>();
        coachWorkload = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие  в расписании
        Group group = trainingSession.getGroup();
        Coach coach = trainingSession.getCoach();
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        if (!timetable.containsKey(dayOfWeek)) {
            Map<TimeOfDay, List<TrainingSession>> map = new TreeMap<>();
            timetable.put(dayOfWeek, map);
        }
        if (!timetable.get(dayOfWeek).containsKey(timeOfDay)) {
            Map<TimeOfDay, List<TrainingSession>> map = timetable.get(dayOfWeek);
            map.put(timeOfDay, new ArrayList<>());
        }
        // добавить проверки на наличие тренировки с одним тренером в одно время
        List<TrainingSession> sessions = timetable.get(dayOfWeek).get(timeOfDay);
        for (TrainingSession session : sessions) {
            if (session.getCoach().equals(trainingSession.getCoach())) {
                return;
            }
        }
        sessions.add(trainingSession);

        if (coachWorkload.containsKey(coach)) {
            coachWorkload.put(coach, coachWorkload.get(coach) + 1);
        } else {
            coachWorkload.put(coach, 1);
        }
    }

    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
        if (coachWorkload.isEmpty()) {
            return new ArrayList<>();
        }
        return coachWorkload.entrySet().stream()
                .sorted(Map.Entry.<Coach, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        Map<TimeOfDay, List<TrainingSession>> result = timetable.get(dayOfWeek);
        if (dayOfWeek != null && result != null) {
            return result.values().stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return new ArrayList<>();
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if (dayOfWeek == null || timeOfDay == null) {
            return new ArrayList<>();
        }
        Map<TimeOfDay, List<TrainingSession>> mapa = timetable.get(dayOfWeek);
        if (mapa == null) {
            return new ArrayList<>();
        }
        List<TrainingSession> list = mapa.get(timeOfDay);
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }
}
