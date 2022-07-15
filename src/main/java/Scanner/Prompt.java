package Scanner;
import Console.LoggerSingleton;
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
    public int askQuetions(int[] questionIndex){
        for(int i : questionIndex){
            log.select(i,questions.get(i));
        }
        log.prompt(":");
        int userChoice = sc.getInt(999);

        if( IntStream.of(questionIndex).anyMatch(x -> x == userChoice)){
            return userChoice;
        }
        askQuetions(questionIndex);
        return 0;
    }
}
