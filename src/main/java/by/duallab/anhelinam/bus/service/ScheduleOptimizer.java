package by.duallab.anhelinam.bus.service;

import by.duallab.anhelinam.bus.entity.Bus;

import java.util.List;

public interface ScheduleOptimizer {
    List<Bus> optimize(List<Bus> schedule);
}
