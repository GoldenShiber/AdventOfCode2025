package Solutions;


import utilities.FileHandling;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Day4 {

    private static final Logger logger = Logger.getLogger(Day4.class.getName());

    public static void solution(List<String> fileInput) {

        // First part 2 highest combinations, 2nd part is 12 highest combinations
        int toiletThreshold = 3;

        logger.info(String.format("The amount of paper rolls reachable after one iteration %d", reachablePaper(fileInput, toiletThreshold)));
        logger.info(String.format("The amount of reachable after multiple iterations is %d", reachablePaperRemove(fileInput, toiletThreshold)));
    }

    private static int reachablePaper(List<String> paperInput, int n){
        List<String> stringList = new ArrayList<>(paperInput);
        int counter = 0;
        int rowLength = stringList.size() , colLength = stringList.getFirst().length();
        for(int i = 0; i < colLength; i++){
            for(int j = 0; j < rowLength; j++){
                if(paperInput.get(j).charAt(i) == '@') {
                    if(isReachable( paperInput, n, i, j)) counter++;
                }
            }
        }
        return counter;
    }

    private static boolean isReachable(List<String> paperInput, int n, int x, int y){
        int counter = 0;
        for(int i = Math.max(0, x-1); i <= Math.min(paperInput.getFirst().length()-1, x+1); i++){
            for(int j = Math.max(0, y-1); j <= Math.min(paperInput.size()-1, y+1); j++){
                if(!(i == x && j == y)){
                    if(paperInput.get(j).charAt(i) == '@'){
                        counter++;
                        if (counter > n){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private static int reachablePaperRemove(List<String> paperInput, int n){
        int counter = -1;
        int newVal = 0;
        List<String> paperList = new ArrayList<>(paperInput);
        int rowLength = paperList.size() , colLength = paperList.getFirst().length();
        while(newVal > counter){
            counter = newVal;
            List<String> tempPaperList = new ArrayList<>(paperList);
            for(int i = 0; i < colLength; i++){
                for(int j = 0; j < rowLength; j++){
                    if(paperList.get(j).charAt(i) == '@') {
                        if(isReachable( paperList, n, i, j)){
                            newVal++;
                            StringBuilder tempString = new StringBuilder(tempPaperList.get(j));
                            tempString.setCharAt(i, '.');
                            tempPaperList.set(j, tempString.toString());
                        }
                    }
                }
            }
            paperList = tempPaperList;
        }

        return counter;
    }


    static void main(String[] args) throws IOException {
        InputStream inputStream = Day4.class.getResourceAsStream("/resources/informationFiles/day4.txt");
        List<String> fileInput = FileHandling.readFromInputAndMakeList(inputStream);
        long startTime = System.nanoTime();
        solution(fileInput);
        long endTime = System.nanoTime();
        logger.info(String.format("The problem took %d milliseconds", ((endTime - startTime) / 100000)));

    }
}
