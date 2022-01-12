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

/*
Жалкая пародия на класс, конфигурирующий зависимости между классами сервисов(типа как в Спринге).
static final для синглтона(он не lazy, потому что нет смысла так сложно, да и все эти объекты мы сразу же используем)
 */
public class AppLauncher {
    private static final BusParser busParser = new BusParserImpl();
    private static final BusReader busReader = new BusReaderImpl(busParser);
    private static final BusWriter busWriter = new BusWriterImpl();
    private static final ScheduleOptimizer optimizer = new ScheduleOptimizerImpl();

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        List<Bus> schedule = busReader.read(fileName);
        busWriter.write(optimizer.optimize(schedule), "output.txt");
    }

}
