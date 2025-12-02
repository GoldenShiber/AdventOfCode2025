package Solutions;


import utilities.FileHandling;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class Day2 {

    private static final Logger logger = Logger.getLogger(Day2.class.getName());

    public static void solution(List<String> fileInput) {

        AtomicLong counters = new AtomicLong();
        AtomicLong partTwoCounter = new AtomicLong();
        String[] splitLine = new String[2];
        fileInput.forEach(line -> {
            String[] lines = line.split(",");
            for (String idPair : lines) {
                String[] subLine = idPair.split("-");
                for (long i = Long.parseLong(subLine[0]); i <= Long.parseLong(subLine[1]); i++) {
                    String id = String.valueOf(i);
                    splitLine[0] = id.substring(0, id.length() / 2);
                    splitLine[1] = id.substring(id.length() / 2);
                    if (splitLine[0].equals(splitLine[1])) {
                        counters.getAndAdd(Long.parseLong(id));
                    }
                    if (pattern(id)) partTwoCounter.getAndAdd(Long.parseLong(id));
                }

            }

        });

        logger.info(String.format("The number of times it lands on 0 is %d", counters.get()));
        logger.info(String.format("The number of times it has hit a 0 is %d", partTwoCounter.get()));
    }

    private static boolean pattern(String line) {
        for (int i = 1; i <= line.length() / 2; i++) {
            if (line.length() % i == 0) {
                int index = 0;
                String subLine = line.substring(index, index + i);
                for (int j = 0; j <= line.length(); j += i) {
                    if (!subLine.equals(line.substring(j, j + i))) break;
                    if (j + i == line.length()) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    static void main(String[] args) throws IOException {
        InputStream inputStream = Day2.class.getResourceAsStream("/resources/informationFiles/day2.txt");
        List<String> fileInput = FileHandling.readFromInputAndMakeList(inputStream);
        long startTime = System.nanoTime();
        solution(fileInput);
        long endTime = System.nanoTime();
        logger.info(String.format("The problem took %d milliseconds", ((endTime - startTime) / 100000)));

    }
}
