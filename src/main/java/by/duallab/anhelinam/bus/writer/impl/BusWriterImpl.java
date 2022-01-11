package by.duallab.anhelinam.bus.writer.impl;

import by.duallab.anhelinam.bus.entity.Bus;
import by.duallab.anhelinam.bus.writer.BusWriter;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BusWriterImpl implements BusWriter {
    @Override
    public void write(List<Bus> schedule, String fileName) {
//        URL filePath = getClass().getResource(fileName);
//        try (Files.createFile(Paths.get(String.valueOf(filePath)))) {
//            for (Bus bus : schedule) {
//                String line = bus.getCompany().toString().charAt(0) + bus.getCompany().toString().toLowerCase() +
//                        " " + bus.getDepartureTime().format(DateTimeFormatter.ofPattern("HH:mm")) +
//                        " " + bus.getArrivalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
//                Files.write(Paths.get(String.valueOf(filePath)), line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}

