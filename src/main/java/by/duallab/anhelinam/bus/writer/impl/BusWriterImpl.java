package by.duallab.anhelinam.bus.writer.impl;

import by.duallab.anhelinam.bus.entity.Bus;
import by.duallab.anhelinam.bus.entity.Company;
import by.duallab.anhelinam.bus.writer.BusWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BusWriterImpl implements BusWriter {
    @Override
    public void write(List<Bus> schedule, String filePath) {
        try {
            StringBuilder line = new StringBuilder();
            boolean flagForEmptyLine = true;
            for (Bus bus : schedule) {
                if (schedule.get(0).getCompany().equals(Company.POSH) &&
                        flagForEmptyLine && bus.getCompany() == Company.GROTTY) {
                    flagForEmptyLine = false;
                    line.append('\n');
                }
                line.append(bus.toString());
            }
            Files.writeString(Paths.get(filePath), line.toString());
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Error writing to file %s: ", filePath), e);
        }
    }
}

