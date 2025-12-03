package Solutions;


import utilities.FileHandling;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class Day3 {

    private static final Logger logger = Logger.getLogger(Day3.class.getName());

    public static void solution(List<String> fileInput) {

        // First part 2 highest combinations, 2nd part is 12 highest combinations
        int part1N = 2;
        int part2N = 12;

        AtomicLong maxVoltage = new AtomicLong();
        AtomicLong maxVoltage12 = new AtomicLong();

        fileInput.forEach(line -> {
           maxVoltage.getAndAdd(maximizeNBattery(line,part1N));
           maxVoltage12.getAndAdd(maximizeNBattery(line, part2N));
        });

        logger.info(String.format("The maximized value for %d numbers is %d", part1N, maxVoltage.get()));
        logger.info(String.format("The maximized value for %d numbers is %d", part2N, maxVoltage12.get()));
    }


    private static long maximizeNBattery(String line, int n){
        //Every battery voltage has n characters
        StringBuilder outputVoltage = new StringBuilder();
        int latestOccupiedIndex = -1;
        while(outputVoltage.length() < n){
            int startIndex = line.length()-n+outputVoltage.length();
            int targetIndex = startIndex -1;
            while(targetIndex > -1 && targetIndex > latestOccupiedIndex){
                if(line.charAt(startIndex)<=line.charAt(targetIndex)) startIndex = targetIndex;
                targetIndex--;
            }
            outputVoltage.append(line.charAt(startIndex));
            latestOccupiedIndex = startIndex;
        }

        return Long.parseLong(outputVoltage.toString());
    }


    static void main(String[] args) throws IOException {
        InputStream inputStream = Day3.class.getResourceAsStream("/resources/informationFiles/day3.txt");
        List<String> fileInput = FileHandling.readFromInputAndMakeList(inputStream);
        long startTime = System.nanoTime();
        solution(fileInput);
        long endTime = System.nanoTime();
        logger.info(String.format("The problem took %d milliseconds", ((endTime - startTime) / 100000)));

    }
}
