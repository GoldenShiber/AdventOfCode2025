package Solutions;


import utilities.FileHandling;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;

public class Day6 {

    private static final Logger logger = Logger.getLogger(Day6.class.getName());

    public static void solution(List<String> fileInput) {

        logger.info(String.format("The value of normal math is %d", processData(fileInput)));
        logger.info(String.format("The value of alien math is %d",  calculateAlienMath(fileInput)));
    }

    private static Long processData(List<String> fileList) {

        String[] stringSplit = fileList.getLast().split("\\s+");
        int columns = stringSplit.length;
        List<Long> numberList = new ArrayList<>();
        List<String> mathList = new ArrayList<>();
        int index = fileList.size() - 2;
        for (String s : stringSplit) {
            mathList.add(s);
            numberList.add(0L);
        }

        while (index >= 0) {
            String[] numbers = fileList.get(index).trim().split("\\s+");
            for (int j = 0; j < columns; j++) {
                switch (mathList.get(j)) {
                    case "+":
                        numberList.set(j, Long.parseLong(numbers[j]) + numberList.get(j));
                        break;
                    case "*":
                        if (numberList.get(j) == 0) {
                            numberList.set(j, Long.parseLong(numbers[j]));
                        } else {
                            numberList.set(j, Long.parseLong(numbers[j]) * numberList.get(j));
                        }
                        break;
                    default:
                        break;
                }

            }
            index--;
        }
        return numberList.stream().mapToLong(Long::longValue).sum();
    }

    private static boolean columnIsEmpty(List<String> lines, int index){
        for (String line : lines) {
            if (!Character.isWhitespace(line.charAt(index))) {
                return false;
            }
        }
        return true;
    }

    private static List<List<String>> generateNumberList(List<String> input){
        int startIndex = 0, currentIndex = 0, matrixIndex = 0;
        List<List<String>> numberLists = new ArrayList<>();
        numberLists.add(new ArrayList<>());
        while(currentIndex<input.getFirst().length()){
            if(columnIsEmpty(input, currentIndex)){
                for(int i = startIndex; i<currentIndex; i++){
                    StringBuilder newNumber = new StringBuilder();
                    for(int j = 0; j<input.size()-1; j++){
                        newNumber.append(input.get(j).charAt(i));
                    }
                    numberLists.get(matrixIndex).add(newNumber.toString());
                }
                numberLists.add(new ArrayList<>());
                matrixIndex++;
                startIndex = currentIndex+1;
            }
            currentIndex++;
        }
        for(int i = startIndex; i<currentIndex; i++){
            StringBuilder newNumber = new StringBuilder();
            for(int j = 0; j<input.size()-1; j++){
                newNumber.append(input.get(j).charAt(i));
            }
            numberLists.get(matrixIndex).add(newNumber.toString());
        }
        return numberLists;
    }

    private static Long calculateAlienMath(List<String> input){
        String[] stringSplit = input.getLast().split("\\s+");
        List<String> signList = new ArrayList<>(Arrays.asList(stringSplit));
        Long sum = 0L;

        List<List<String>> numberLists = generateNumberList(input);
        for(int j = 0; j< numberLists.size(); j++){
            sum += funnyCalculation(numberLists.get(j), signList.get(j).charAt(0));
        }

        return sum;
    }

    private static Long funnyCalculation(List<String> lines, Character sign){
        StringBuilder number = new StringBuilder();
        List<Long> numbers = new ArrayList<>();
        for(String line: lines){
            number = new StringBuilder();
            for(int i = 0; i<line.length(); i++){
                if(!Character.isWhitespace(line.charAt(i))){
                    number.append(line.charAt(i));
                }
            }
            numbers.add(Long.parseLong(number.toString()));
            }
        if(sign=='+') {
            return numbers.stream().mapToLong(Long::longValue).sum();
        } else if(sign =='*'){
            return numbers.stream().reduce(1L, Math::multiplyExact);
        }
        return 1L;
    }

    static void main(String[] args) throws IOException {
        InputStream inputStream = Day6.class.getResourceAsStream("/resources/informationFiles/day6.txt");
        InputStream inputStreamExample = Day6.class.getResourceAsStream("/resources/informationFiles/day6Example.txt");

        List<String> fileInput = FileHandling.readFromInputAndMakeList(inputStream);
        List<String> exampleFileInput = FileHandling.readFromInputAndMakeList(inputStreamExample);
        long startTime = System.nanoTime();
        solution(fileInput);
        solution(exampleFileInput);
        long endTime = System.nanoTime();
        logger.info(String.format("The problem took %d milliseconds", ((endTime - startTime) / 100000)));

    }
}
