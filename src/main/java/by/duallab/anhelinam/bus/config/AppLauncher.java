package by.duallab.anhelinam.bus.config;

import by.duallab.anhelinam.bus.entity.Bus;
import by.duallab.anhelinam.bus.reader.BusParser;
import by.duallab.anhelinam.bus.reader.BusReader;
import by.duallab.anhelinam.bus.reader.impl.BusParserImpl;
import by.duallab.anhelinam.bus.reader.impl.BusReaderImpl;
import by.duallab.anhelinam.bus.writer.BusWriter;
import by.duallab.anhelinam.bus.writer.impl.BusWriterImpl;

import java.util.List;
import java.util.Scanner;

public class AppLauncher {
    private static final BusParser busParser = new BusParserImpl();
    private static final BusReader busReader = new BusReaderImpl(busParser);
    private static final BusWriter busWriter = new BusWriterImpl();

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        List<Bus> schedule = busReader.read(fileName);
        busWriter.write(schedule, "output.txt");
    }

}
