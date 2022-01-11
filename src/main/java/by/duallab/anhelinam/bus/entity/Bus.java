package by.duallab.anhelinam.bus.entity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Bus {
    private Company company;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int duration;
    private static final int MINUTES_PER_DAY = 24 * 60;

    public Bus(Company company, LocalTime departureTime, LocalTime arrivalTime) {
        this.company = company;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        int timeDifference = (this.arrivalTime.toSecondOfDay() - this.departureTime.toSecondOfDay()) / 60;
        this.duration = timeDifference >= 0 ? timeDifference : MINUTES_PER_DAY + timeDifference;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        return company == bus.company && Objects.equals(departureTime, bus.departureTime) && Objects.equals(arrivalTime, bus.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, departureTime, arrivalTime, duration);
    }

    @Override
    public String toString() { //временно
        return company.toString().charAt(0) + company.toString().toLowerCase().substring(1) +
                " " + departureTime.format(DateTimeFormatter.ofPattern("HH:mm")) +
                " " + arrivalTime.format(DateTimeFormatter.ofPattern("HH:mm")) +
                ", duration = " + duration;
    }
}
