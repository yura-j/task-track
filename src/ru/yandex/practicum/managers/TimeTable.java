package ru.yandex.practicum.managers;

import ru.yandex.practicum.models.TimeTableGridNode;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Predicate;

public class TimeTable {
    LocalDateTime origin;
    LocalDateTime end;

    final static int gridStepInMinutes = 15;

    TimeTableGridNode[] grid;

    public TimeTable() {
        origin = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        end = origin.plusYears(1);
        int minutes = (int) Duration.between(origin, end).toMinutes();
        int gridSize = 1 + minutes / gridStepInMinutes;
        grid = new TimeTableGridNode[gridSize];
    }

    public boolean isIntervalFreeInTable(LocalDateTime startTime, int duration) {
        return iterateGridOverIntervalReturnBooleanMultiplication(startTime, duration, TimeTableGridNode::canHold);
    }

    public void holdInterval(LocalDateTime startTime, int duration) {
        iterateGridOverIntervalReturnBooleanMultiplication(startTime, duration, TimeTableGridNode::hold);
    }

    public void freeInterval(LocalDateTime startTime, int duration) {
        iterateGridOverIntervalReturnBooleanMultiplication(startTime, duration, TimeTableGridNode::free);
    }

    private boolean iterateGridOverIntervalReturnBooleanMultiplication(LocalDateTime startTime, int duration, Predicate<TimeTableGridNode> operator) {
        int startNodeIndex = getClosestGridIndex(startTime);
        int affectedNodesCount = 1 + duration / gridStepInMinutes;
        boolean allOperationsSuccess = true;

        int i = startNodeIndex;
        while (startNodeIndex >= 0 && i < startNodeIndex + affectedNodesCount) {
            int gridIndex = i++;
            TimeTableGridNode node = getNode(gridIndex);
            allOperationsSuccess = allOperationsSuccess && operator.test(node);
        }
        return allOperationsSuccess;
    }

    private TimeTableGridNode getNode(int gridIndex) {
        TimeTableGridNode node = grid[gridIndex];
        if (node == null) {
            node = new TimeTableGridNode();
        }
        return node;
    }

    private int getClosestGridIndex(LocalDateTime time) {
        int minutes = (int) Duration.between(origin, time).toMinutes();
        return minutes / gridStepInMinutes;
    }
}
