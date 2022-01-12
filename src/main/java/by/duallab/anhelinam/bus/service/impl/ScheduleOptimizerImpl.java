package by.duallab.anhelinam.bus.service.impl;

import by.duallab.anhelinam.bus.entity.Bus;
import by.duallab.anhelinam.bus.service.ScheduleOptimizer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScheduleOptimizerImpl implements ScheduleOptimizer {

    /**
     * Optimizes the schedule, which is represented by a list of {@link Bus}.
     * <p>
     * The main idea (ascending for both departure and arrival time)
     * <p>
     * The algorithm consists of 2 steps:
     * <p>
     * 1) Excluding buses that travel longer than 60 minutes; sorting them in reverse order
     * using {@link Bus#compareTo(Bus)}.
     * <p>
     * 2) Iterating through the sorted list replacing items with the latest most optimal one encountered.
     * By 'most optimal' the following is implied: Bus A is more optimal than Bus B if A's arrival time
     * is before B's one(see {@link #chooseEarlierArrivalTimeBus(Bus, Bus)}). The ordering of the list ensures that
     * we visit more optimal Buses within the same departure time.
     *
     * @param schedule
     * @return
     */
    @Override
    public List<Bus> optimize(List<Bus> schedule) {
        schedule = excludeInvalidAndReverseSort(schedule);

        schedule = removeForAscendingArrival(schedule, false);
        List<Bus> nearMidnightSchedule = removeForAscendingArrival(schedule.stream()
                .filter(this::isNearMidnight)
                .sorted(Comparator.comparingInt(o -> o.getDepartureTime().getHour()))
                .collect(Collectors.toList()), true);
        System.out.println(nearMidnightSchedule);
        return mergeEntireAndNearMidnightSchedules(schedule, nearMidnightSchedule);
    }

    private List<Bus> excludeInvalidAndReverseSort(List<Bus> schedule) {
        return schedule.stream()
                .filter(p -> p.getDuration() <= 60)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    private List<Bus> removeForAscendingArrival(List<Bus> schedule, boolean nearMidnight) {
        if (schedule.isEmpty()) {
            return Collections.emptyList();
        }
        System.out.println(schedule);
        AtomicReference<Bus> currentBus = new AtomicReference<>(schedule.get(0));
        return schedule.stream()
                .map(bus -> {
                    Bus chosen = chooseEarlierArrivalTimeBus(currentBus.get(), bus, nearMidnight);
                    currentBus.set(chosen);
                    return currentBus.get();
                }).distinct().collect(Collectors.toList());
    }

    private Bus chooseEarlierArrivalTimeBus(Bus currentBus, Bus otherBus, boolean nearMidnight) {
        if (nearMidnight) {
            return currentBus.getArrivalTime().isAfter(otherBus.getArrivalTime()) ? otherBus : currentBus;
        }
        return currentBus.getDepartureTime().toSecondOfDay() / 60 + currentBus.getDuration() >
                otherBus.getDepartureTime().toSecondOfDay() / 60 + otherBus.getDuration() ?
                otherBus : currentBus;
    }

    private List<Bus> mergeEntireAndNearMidnightSchedules(List<Bus> entireSchedule, List<Bus> nearMidnightSchedule) {
        return Stream.concat(entireSchedule.stream().filter(p -> !isNearMidnight(p)), nearMidnightSchedule.stream())
                .sorted((o1, o2) -> {
                    if (o1.getCompany().equals(o2.getCompany())) {
                        return o1.getDepartureTime().compareTo(o2.getDepartureTime());
                    }
                    return o1.getCompany().ordinal() - o2.getCompany().ordinal();
                })
                .collect(Collectors.toList());
    }

    private boolean isNearMidnight(Bus bus) {
        return bus.getArrivalTime().getHour() == 0;
    }

}
