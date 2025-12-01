package Solutions;


import utilities.FileHandling;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Day1 {

    private static final Logger logger = Logger.getLogger(Day1.class.getName());

    public static void solution(List<String> fileInput) {
        final char[] direction = {'.'};
        final int[] currentPoint = {50};
        final int[] move = {1};
        AtomicInteger rotated = new AtomicInteger();
        AtomicInteger counters = new AtomicInteger();
        AtomicInteger rotation = new AtomicInteger();
        AtomicInteger oldValue = new AtomicInteger();
        oldValue.set(50);


        fileInput.forEach(line -> {
            direction[0] = line.charAt(0);
            rotation.getAndSet(Integer.parseInt(line.substring(1)));
            move[0] = rotation.get() % 100;
            currentPoint[0] = (direction[0] == 'R') ? currentPoint[0] + move[0] : currentPoint[0] - move[0];
            rotated.getAndAdd(rotation.get() / 100);

            if (currentPoint[0] % 100 == 0) {
                counters.getAndIncrement();
            } else if (currentPoint[0] > 100) {
                currentPoint[0] = currentPoint[0] % 100;
                if (oldValue.get() != 0 && oldValue.get() != 100) rotated.getAndIncrement();
            } else if (currentPoint[0] < 0) {
                currentPoint[0] = 100 + currentPoint[0];
                if (oldValue.get() != 0 && oldValue.get() != 100) rotated.getAndIncrement();
            }
            oldValue.set(currentPoint[0]);
        });

        logger.info(String.format("The number of times it lands on 0 is %d", counters.get()));
        logger.info(String.format("The number of times it has hit a 0 is %d", (rotated.get()) + counters.get()));
    }

    static void main(String[] args) throws IOException {
        InputStream inputStream = Day1.class.getResourceAsStream("/resources/informationFiles/day1.txt");
        List<String> fileInput = FileHandling.readFromInputAndMakeList(inputStream);
        long startTime = System.nanoTime();
        solution(fileInput);
        long endTime = System.nanoTime();
        logger.info(String.format("The problem took %d milliseconds", ((endTime - startTime) / 100000)));

    }
}
