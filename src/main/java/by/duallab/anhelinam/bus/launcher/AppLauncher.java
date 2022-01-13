package by.duallab.anhelinam.bus.launcher;

import by.duallab.anhelinam.bus.entity.Bus;
import by.duallab.anhelinam.bus.reader.BusParser;
import by.duallab.anhelinam.bus.reader.BusReader;
import by.duallab.anhelinam.bus.reader.impl.BusParserImpl;
import by.duallab.anhelinam.bus.reader.impl.BusReaderImpl;
import by.duallab.anhelinam.bus.service.ScheduleOptimizer;
import by.duallab.anhelinam.bus.service.impl.ScheduleOptimizerImpl;
import by.duallab.anhelinam.bus.writer.BusWriter;
import by.duallab.anhelinam.bus.writer.impl.BusWriterImpl;

import java.util.List;
import java.util.Scanner;

public class AppLauncher {
    private static final BusParser BUS_PARSER = new BusParserImpl();
    private static final BusReader BUS_READER = new BusReaderImpl(BUS_PARSER);
    private static final BusWriter BUS_WRITER = new BusWriterImpl();
    private static final ScheduleOptimizer OPTIMIZER = new ScheduleOptimizerImpl();

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        List<Bus> schedule = BUS_READER.read(fileName);
        BUS_WRITER.write(OPTIMIZER.optimize(schedule), "output.txt");
    }

}
