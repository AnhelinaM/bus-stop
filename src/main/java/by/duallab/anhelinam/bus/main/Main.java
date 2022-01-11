package by.duallab.anhelinam.bus.main;

import by.duallab.anhelinam.bus.entity.Bus;
import by.duallab.anhelinam.bus.reader.BusParser;
import by.duallab.anhelinam.bus.reader.BusReader;
import by.duallab.anhelinam.bus.reader.impl.BusParserImpl;
import by.duallab.anhelinam.bus.reader.impl.BusReaderImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//       в сервисах??
        Scanner scanner = new Scanner(System.in);
        BusParser busParser = new BusParserImpl();
        BusReader busReader = new BusReaderImpl(busParser);
        String fileName = scanner.nextLine();
        List<Bus> schedule = new ArrayList<>();
        schedule = busReader.read(fileName);

    }

}
