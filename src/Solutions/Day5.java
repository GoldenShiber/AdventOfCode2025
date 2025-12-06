package Solutions;


import utilities.FileHandling;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class Day5 {

    private static final Logger logger = Logger.getLogger(Day5.class.getName());

    public static void solution(List<String> fileInput) {

        List<List<String>> dataSplit = prepareData(fileInput);

       logger.info(String.format("The numbers in intervals is %d", processData(dataSplit.getFirst(), dataSplit.getLast())));
       logger.info(String.format("The number of legal potential values is %d",  interValCalculations(dataSplit.getFirst())));
    }

    private static List<String> intervalMerge(String interval, List<String> intervalList){
        for(int i = 0; i<intervalList.size(); i++){
            if(!intervalList.get(i).equals(interval) && !insideAndMerge(interval, intervalList.get(i)).isEmpty()){
                String newInterval = insideAndMerge(interval, intervalList.get(i));
                intervalList.set(i,newInterval);
                Set<String> hashSet = new HashSet<>(intervalList);
                intervalList = new ArrayList<>(hashSet);
                return intervalMerge(newInterval, intervalList);
            }
        }
        intervalList.add(interval);
        Set<String> hashSet = new HashSet<>(intervalList);
        intervalList = new ArrayList<>(hashSet);
        return intervalList;
    }

    private static List<List<String>> prepareData(List<String> fileInput){
        List<List<String>> numberLists = new ArrayList<>();
        int loop = 0;
        List<String> intervalList = new ArrayList<>();
        List<String> numberList= new ArrayList<>();
        for(String line : fileInput){
            if(line.isEmpty()){
                loop++;
            } else {
                if (loop == 0) {
                    intervalList = intervalMerge(line, intervalList);
                } else {
                    numberList.add(line);
                }
            }
        }
        numberLists.add(intervalList);
        numberLists.add(numberList);
        return numberLists;
    }

    private static int processData(List<String> intervals, List<String> numbers){
        int counter = 0;
        for(String number : numbers){
            long num = Long.parseLong(number);
            for(String interval : intervals){
                String[] intVals = interval.split("-");
                long low = Long.parseLong(intVals[0]), high = Long.parseLong(intVals[1]);
                if(num >= low && num <= high) {
                    counter++;
                    break;
                }
            }
        }
        return counter;
    }

    private static Long interValCalculations(List<String> intervals){
        long counter = 0;
        for(String interval : intervals){
            String[] intVal = interval.split("-");
            counter += Long.parseLong(intVal[1])-Long.parseLong(intVal[0]) +1;
        }
        return counter;
    }

    private static String insideAndMerge(String newInterval, String compareInterval){
        String[] newIntervalValues = newInterval.split("-");
        String[] compareIntervalValues = compareInterval.split("-");
        long ADot = Long.parseLong(newIntervalValues[0]), BDot = Long.parseLong(newIntervalValues[1]);
        long A = Long.parseLong(compareIntervalValues[0]), B = Long.parseLong(compareIntervalValues[1]);
        if((ADot >= A && ADot <= B) || (BDot >= A && BDot <= B) || (ADot <= A && BDot >= B)){
            return Math.min(A, ADot) + "-" + Math.max(B, BDot);
        }
        return "";
    }

    static void main(String[] args) throws IOException {
        InputStream inputStream = Day5.class.getResourceAsStream("/resources/informationFiles/day5.txt");
        InputStream inputStreamExample = Day5.class.getResourceAsStream("/resources/informationFiles/day5Example.txt");

        List<String> fileInput = FileHandling.readFromInputAndMakeList(inputStream);
        List<String> exampleFileInput = FileHandling.readFromInputAndMakeList(inputStreamExample);
        long startTime = System.nanoTime();
        solution(fileInput);
        solution(exampleFileInput);
        long endTime = System.nanoTime();
        logger.info(String.format("The problem took %d milliseconds", ((endTime - startTime) / 100000)));

    }
}
