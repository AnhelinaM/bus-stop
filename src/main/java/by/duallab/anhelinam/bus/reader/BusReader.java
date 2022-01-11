package by.duallab.anhelinam.bus.reader;

import by.duallab.anhelinam.bus.entity.Bus;

import java.util.List;

public interface BusReader {
    List<Bus> read(String fileName);
}
