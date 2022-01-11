package by.duallab.anhelinam.bus.reader.impl;

import by.duallab.anhelinam.bus.entity.Bus;
import by.duallab.anhelinam.bus.reader.BusParser;
import by.duallab.anhelinam.bus.reader.BusReader;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BusReaderImpl implements BusReader {
    private final BusParser busParser;

    public BusReaderImpl(BusParser busParser) {
        this.busParser = busParser;
    }

    @Override
    public List<Bus> read(String filePath) {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            return stream.map(str -> busParser.parseLineParts(str.split(" "))).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
