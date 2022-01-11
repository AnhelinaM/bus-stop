package by.duallab.anhelinam.bus.writer;

import by.duallab.anhelinam.bus.entity.Bus;

import java.util.List;

public interface BusWriter {
    void write(List<Bus> schedule, String fileName);
}
