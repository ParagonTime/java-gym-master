package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final TreeMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;


    public Timetable() {
        timetable = new TreeMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие  в расписании
        Group group = trainingSession.getGroup();
        Coach coach = trainingSession.getCoach();
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        if (!timetable.containsKey(dayOfWeek)) {
            TreeMap<TimeOfDay, List<TrainingSession>> map = new TreeMap<>();
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


    }

    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {

        List<TrainingSession> listTraining = timetable.entrySet().stream()
                .flatMap((x) -> x.getValue().entrySet().stream()
                        .flatMap(y -> y.getValue().stream()))
                .toList();
        Map<Coach, Integer> mapCount = new HashMap<>();
        listTraining.forEach(x -> mapCount.put(x.getCoach(), mapCount.getOrDefault(x.getCoach(), 0) + 1));
        return mapCount.entrySet().stream()
                .sorted((o1, o2) -> o2.getValue() - o1.getValue())
                .toList();
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if (dayOfWeek == null) return new TreeMap<>();
        TreeMap<TimeOfDay, List<TrainingSession>> result = timetable.get(dayOfWeek);
        if (result != null) {
            return result;
        }
        return new TreeMap<>();
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
        return mapa.getOrDefault(timeOfDay, Collections.emptyList());
    }
}
