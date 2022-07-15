package Scanner;
import Console.LoggerSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Prompt {
    Map<Integer,String> questions;
    LoggerSingleton log;
    ScannerSingleton sc;
    public Prompt(Map<Integer,String> questions){
        this.log =  LoggerSingleton.getInstance();
        this.sc = ScannerSingleton.getInstance();
        this.questions = questions;
    }
    /**
     * Asks questions out of the user selectable index.
     * TODO Test for invalid indexes.
     * @param questionIndex
     * @return
     */
    public int chooseMulti(int[] questionIndex){
        for(int i : questionIndex){
            log.select(i,questions.get(i));
        }
        log.prompt(":");
        int userChoice = sc.getInt(0,999);
        if( IntStream.of(questionIndex).anyMatch(x -> x == userChoice)){
            return userChoice;
        }
        return chooseMulti(questionIndex);
    }

    public List<String> askSequence(int[] questionIndex){
        List<String> answers = new ArrayList<String>();
        for(int i : questionIndex){
            log.prompt(questions.get(i));
            log.prompt(":");
            if(i > 1000 && i < 2000){
                answers.add(sc.getString());
            } else if(i > 2000) {
                answers.add("");
            } else {
                answers.add(String.valueOf(sc.getInt(0,999)));
            }
        }
        System.out.println(answers);
        return answers;
    }
}
