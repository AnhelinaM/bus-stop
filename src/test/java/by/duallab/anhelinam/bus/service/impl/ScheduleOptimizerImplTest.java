package by.duallab.anhelinam.bus.service.impl;

import by.duallab.anhelinam.bus.entity.Bus;
import by.duallab.anhelinam.bus.entity.Company;
import by.duallab.anhelinam.bus.service.ScheduleOptimizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScheduleOptimizerImplTest {
    private List<Bus> schedule;
    private List<Bus> expectedResult;
    private static final ScheduleOptimizer OPTIMIZER = new ScheduleOptimizerImpl();

    @BeforeEach
    public void prepare() {
        schedule = new ArrayList<>();
        expectedResult = new ArrayList<>();
    }

    @Test
    public void testOptimizeNoGivenBus() {
        assertEquals(expectedResult, OPTIMIZER.optimize(schedule));
    }

    @Test
    public void testOptimizeGivenBusWithDurationMoreThanHour() {
        Bus bus1 = new Bus(Company.POSH, LocalTime.of(12, 45), LocalTime.of(13, 50));
        Collections.addAll(schedule, bus1);
        assertEquals(expectedResult, OPTIMIZER.optimize(schedule));
    }

    @Test
    public void testOptimizeGivenSameBusesFromDifferentCompanies() {
        Bus bus1 = new Bus(Company.POSH, LocalTime.of(12, 45), LocalTime.of(13, 5));
        Bus bus2 = new Bus(Company.GROTTY, LocalTime.of(12, 45), LocalTime.of(13, 5));
        Collections.addAll(schedule, bus1, bus2);
        Collections.addAll(expectedResult, bus1);
        assertEquals(expectedResult, OPTIMIZER.optimize(schedule));
    }

    @Test
    public void testOptimizeGivenBusesWithTheSameDepartureTime() {
        Bus bus1 = new Bus(Company.POSH, LocalTime.of(12, 45), LocalTime.of(13, 15));
        Bus bus2 = new Bus(Company.GROTTY, LocalTime.of(12, 45), LocalTime.of(13, 5));
        Collections.addAll(schedule, bus1, bus2);
        Collections.addAll(expectedResult, bus2);
        assertEquals(expectedResult, OPTIMIZER.optimize(schedule));
    }

    @Test
    public void testOptimizeGivenBusesWithTheSameDepartureTimeNearMidnight() {
        Bus bus1 = new Bus(Company.POSH, LocalTime.of(23, 48), LocalTime.of(0, 10));
        Bus bus2 = new Bus(Company.POSH, LocalTime.of(23, 48), LocalTime.of(23, 58));
        Collections.addAll(schedule, bus1, bus2);
        Collections.addAll(expectedResult, bus2);
        assertEquals(expectedResult, OPTIMIZER.optimize(schedule));
    }

    @Test
    public void testOptimizeGivenBusesWithTheSameArrivalTime() {
        Bus bus1 = new Bus(Company.POSH, LocalTime.of(12, 55), LocalTime.of(13, 15));
        Bus bus2 = new Bus(Company.GROTTY, LocalTime.of(12, 45), LocalTime.of(13, 15));
        Collections.addAll(schedule, bus1, bus2);
        Collections.addAll(expectedResult, bus1);
        assertEquals(expectedResult, OPTIMIZER.optimize(schedule));
    }

    @Test
    public void testOptimizeGivenBusesWithTheSameArrivalTimeNearMidnight() {
        Bus bus1 = new Bus(Company.POSH, LocalTime.of(23, 55), LocalTime.of(0, 15));
        Bus bus2 = new Bus(Company.GROTTY, LocalTime.of(0, 5), LocalTime.of(0, 15));
        Collections.addAll(schedule, bus1, bus2);
        Collections.addAll(expectedResult, bus2);
        assertEquals(expectedResult, OPTIMIZER.optimize(schedule));
    }

    @Test
    public void testOptimizeGivenBusesOneMoreOptimal() {
        Bus bus1 = new Bus(Company.POSH, LocalTime.of(12, 45), LocalTime.of(13, 15));
        Bus bus2 = new Bus(Company.GROTTY, LocalTime.of(12, 55), LocalTime.of(13, 5));
        Collections.addAll(schedule, bus1, bus2);
        Collections.addAll(expectedResult, bus2);
        assertEquals(expectedResult, OPTIMIZER.optimize(schedule));
    }

    @Test
    public void testOptimizeGivenBusesAllOptimal() {
        Bus bus1 = new Bus(Company.GROTTY, LocalTime.of(12, 44), LocalTime.of(13, 14));
        Bus bus2 = new Bus(Company.POSH, LocalTime.of(12, 45), LocalTime.of(13, 15));
        Bus bus3 = new Bus(Company.POSH, LocalTime.of(12, 56), LocalTime.of(13, 16));
        Collections.addAll(schedule, bus1, bus2, bus3);
        Collections.addAll(expectedResult, bus2, bus3, bus1);
        assertEquals(expectedResult, OPTIMIZER.optimize(schedule));
    }

    @Test
    public void testOptimizeGivenBusesWithArrivalTimeBeforeAndAfterMidnight() {
        Bus bus1 = new Bus(Company.POSH, LocalTime.of(23, 47), LocalTime.of(23, 55));
        Bus bus2 = new Bus(Company.GROTTY, LocalTime.of(23, 46), LocalTime.of(0, 21));
        Collections.addAll(schedule, bus1, bus2);
        Collections.addAll(expectedResult, bus1);
        assertEquals(expectedResult, OPTIMIZER.optimize(schedule));
    }

    @Test
    public void testOptimizeGivenBusesWithDepartureTimeBeforeAndAfterMidnight() {
        Bus bus1 = new Bus(Company.POSH, LocalTime.of(0, 10), LocalTime.of(0, 30));
        Bus bus2 = new Bus(Company.GROTTY, LocalTime.of(23, 50), LocalTime.of(0, 40));
        Collections.addAll(schedule, bus1, bus2);
        Collections.addAll(expectedResult, bus1);
        assertEquals(expectedResult, OPTIMIZER.optimize(schedule));
    }

    @Test
    public void testOptimizeGivenBusesWithDepartureTimeBeforeMidnightAndArrivalTimeAfterMidnight() {
        Bus bus1 = new Bus(Company.POSH, LocalTime.of(23, 51), LocalTime.of(0, 11));
        Bus bus2 = new Bus(Company.POSH, LocalTime.of(23, 49), LocalTime.of(0, 12));
        Collections.addAll(schedule, bus1, bus2);
        Collections.addAll(expectedResult, bus1);
        assertEquals(expectedResult, OPTIMIZER.optimize(schedule));
    }

    @Test
    public void testOptimizeGivenBusesWithDepartureTimeNearMidnightAndDuringTheDay() {
        Bus bus1 = new Bus(Company.POSH, LocalTime.of(0, 10), LocalTime.of(0, 20));//
        Bus bus2 = new Bus(Company.POSH, LocalTime.of(13, 40), LocalTime.of(13, 50));
        Bus bus3 = new Bus(Company.POSH, LocalTime.of(23, 55), LocalTime.of(0, 30));
        Collections.addAll(schedule, bus1, bus2, bus3);
        Collections.addAll(expectedResult, bus1, bus2);
        assertEquals(expectedResult, OPTIMIZER.optimize(schedule));
    }
}