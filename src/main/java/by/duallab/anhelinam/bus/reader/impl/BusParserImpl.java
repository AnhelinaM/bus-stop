package by.duallab.anhelinam.bus.reader.impl;

import by.duallab.anhelinam.bus.entity.Bus;
import by.duallab.anhelinam.bus.entity.Company;
import by.duallab.anhelinam.bus.reader.BusParser;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BusParserImpl implements BusParser {
    @Override
    public Bus parseLineParts(String[] lineParts) {
        try {
            Company company = Company.valueOf(lineParts[0].toUpperCase());
            LocalTime departureTime = LocalTime.parse(lineParts[1], DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime arrivalTime = LocalTime.parse(lineParts[2], DateTimeFormatter.ofPattern("HH:mm"));
            return new Bus(company, departureTime, arrivalTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
