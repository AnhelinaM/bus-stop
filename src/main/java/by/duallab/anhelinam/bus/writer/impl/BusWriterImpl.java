package by.duallab.anhelinam.bus.writer.impl;

import by.duallab.anhelinam.bus.entity.Bus;
import by.duallab.anhelinam.bus.writer.BusWriter;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BusWriterImpl implements BusWriter {
    @Override
    public void write(List<Bus> schedule, String filePath) {
        try {
            StringBuilder line = new StringBuilder();
            for (Bus bus : schedule) {
                line.append(bus.toString()).append('\n');
            }
            Files.writeString(Paths.get(filePath), line.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

