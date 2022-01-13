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
     * The main idea (ascending for both departure and arrival time) O(n log n) todo
     * <p>
     * The algorithm consists of 4 steps:
     * <p>
     * 1) Excluding buses that travel longer than 60 minutes;
     * sorting them in reverse order using {@link Bus#compareTo(Bus)}.
     * <p>
     * 2) Iterating through the sorted list replacing items with the latest most optimal one encountered.
     * By 'most optimal' the following is considered: Bus A is more optimal than Bus B if A's arrival time
     * is before B's one (see {@link #chooseEarlierArrivalTimeBus(Bus, Bus, boolean)}).
     * The ordering of the list ensures that we visit more optimal Buses within the same departure time.
     * <p>
     * 3) Choosing Buses that arrive after midnight (from 00:00 to 00:59). Reverse sorting them
     * by departure time in order from 23:00 to 00:59.
     * The ordering of the list allows the selection of optimal buses that arrive the next day.
     * Doing step 2 with this list.
     * <p>
     * 4) Merging two lists to obtain optimal schedule
     * (see {@link #mergeEntireAndNearMidnightSchedules(List, List)}).
     *
     * @param schedule List of {@link Bus} to optimize
     * @return list of {@link Bus} which is merged from two lists
     * obtained in the first and the second parts of the algorithm
     */
    @Override
    public List<Bus> optimize(List<Bus> schedule) {
        schedule = excludeInvalidAndReverseSort(schedule);
        schedule = removeForAscendingArrival(schedule, false);
        List<Bus> nearMidnightSchedule = removeForAscendingArrival(schedule.stream()
                .filter(this::isNearMidnight)
                .sorted(Comparator.comparingInt(o -> o.getDepartureTime().getHour()))
                .collect(Collectors.toList()), true);
        return mergeEntireAndNearMidnightSchedules(schedule, nearMidnightSchedule);
    }

    /* Excluding Buses that travel longer than 60 minutes;
     * sorting them in reverse order using Bus::compareTo */
    private List<Bus> excludeInvalidAndReverseSort(List<Bus> schedule) {
        return schedule.stream()
                .filter(p -> p.getDuration() <= 60)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    /* Iterating through the list replacing items with the latest one
     * with the earlier arrival time encountered.
     * Leaving distinct Buses (so, among two identical Buses, one will remain in the schedule).
     *
     * The algorithm assumes that the argument is a sorted array.
     * The ordering of the list ensures that we visit more optimal Buses within the same departure time. */
    private List<Bus> removeForAscendingArrival(List<Bus> schedule, boolean nearMidnight) {
        if (schedule.isEmpty()) {
            return Collections.emptyList();
        }
        AtomicReference<Bus> currentBus = new AtomicReference<>(schedule.get(0));
        return schedule.stream()
                .map(bus -> {
                    Bus chosen = chooseEarlierArrivalTimeBus(currentBus.get(), bus, nearMidnight);
                    currentBus.set(chosen);
                    return currentBus.get();
                }).distinct().collect(Collectors.toList());
    }

    /* Returns the bus that arrived earlier.
     *
     * If the bus arrives after midnight, simply compare the arrival time.
     * Otherwise, the duration of trips is compared from the beginning of the day of departure
     * to the time of arrival (it may be more than a day for cases
     * when the time of departure is later than the time of arrival) */
    private Bus chooseEarlierArrivalTimeBus(Bus currentBus, Bus otherBus, boolean nearMidnight) {
        if (nearMidnight) {
            return currentBus.getArrivalTime().isAfter(otherBus.getArrivalTime()) ? otherBus : currentBus;
        }
        return currentBus.getDepartureTime().toSecondOfDay() / 60 + currentBus.getDuration() >
                otherBus.getDepartureTime().toSecondOfDay() / 60 + otherBus.getDuration() ?
                otherBus : currentBus;
    }

    /* Concatenation of two lists.
     * Sorting obtained list of Buses in the following order:
     * - descending for company (Grotty < Posh)
     * - ascending for departure time
     */
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
