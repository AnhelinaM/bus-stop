package by.duallab.anhelinam.bus.reader;

import by.duallab.anhelinam.bus.entity.Bus;

public interface BusParser {
    Bus parseLineParts(String[] lineParts);
}
