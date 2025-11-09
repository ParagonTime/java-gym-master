package ru.yandex.practicum.gym;

import org.junit.Test;


import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimetableTest {

    @Test
    public void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        List<TrainingSession> list = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1,list.size());
        //Проверить, что за вторник не вернулось занятий
        list = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertEquals(0, list.size());
    }

    @Test
    public void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        List<TrainingSession> list = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1,list.size());
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        list = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, list.size());
        TimeOfDay time13 = new TimeOfDay(13, 0);
        TimeOfDay time20 = new TimeOfDay(20, 0);
        assertEquals(time13, list.get(0).getTimeOfDay());
        assertEquals(list.get(1).getTimeOfDay(), time20);
        // Проверить, что за вторник не вернулось занятий
        list = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertEquals(0, list.size());
    }

    @Test
    public void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<TrainingSession> result = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertEquals(1, result.size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        result = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertEquals(0, result.size());
    }

    @Test
    public void testGetTrainingSessionsForDayWhenTimetableIsEmpty() {
        Timetable timetable = new Timetable();

        List<TrainingSession> list = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(0, list.size());
    }

    @Test
    public void testGetTrainingSessionsForDayAndTimeWhenTimetableIsEmpty() {
        Timetable timetable = new Timetable();

        List<TrainingSession> list = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        assertEquals(0, list.size());
    }

    @Test
    public void testGetTrainingSessionsForDayWhenArgumentsIsNull() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> list = timetable.getTrainingSessionsForDay(null);
        assertEquals(0, list.size());
    }

    @Test
    public void testGetTrainingSessionsForDayAndTimeWhenArgumentsIsNull() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> list = timetable.getTrainingSessionsForDayAndTime(null, null);
        assertEquals(0, list.size());
        list = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, null);
        assertEquals(0, list.size());
        list = timetable.getTrainingSessionsForDayAndTime(null, new TimeOfDay(10, 0));
        assertEquals(0, list.size());
    }

    @Test
    public void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        singleTrainingSession = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0));
        timetable.addNewTrainingSession(singleTrainingSession);

        Coach coach2 = new Coach("Иванов", "Иван", "Иванович");
        singleTrainingSession = new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0));
        timetable.addNewTrainingSession(singleTrainingSession);

        var list = timetable.getCountByCoaches();
        assertEquals(coach1, list.get(0).getKey());
        assertEquals(coach2, list.get(1).getKey());

        assertTrue(list.get(0).getValue() - list.get(1).getValue() > 0);
    }

    @Test
    public void testGetCountByCoachesWhenCoachWorkloadIsNull() {
        Timetable timetable = new Timetable();

        var list = timetable.getCountByCoaches();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testGetOneItemListWhenAddOneTrainingSessionTwo() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> list = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        assertEquals(1, list.size());
    }

    @Test
    public void testGetOneItemListWhenAddOneTrainingSessionThree() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> list = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        assertEquals(1, list.size());
    }
}
