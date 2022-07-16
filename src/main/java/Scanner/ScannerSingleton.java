package Scanner;
import Console.LoggerSingleton;

import java.util.Locale;
import java.util.Scanner;
public class ScannerSingleton {
    private static ScannerSingleton scan = null;
    private Scanner scanner;
    private LoggerSingleton log;
    private ScannerSingleton(){
        scanner = new Scanner(System.in);
    }
    public static ScannerSingleton getInstance(){
        if(scan == null){
            scan = new ScannerSingleton();
        }
        return scan;
    }
    public void close(){
        scanner.close();
    }
    /**
     * @return
     */
    public String getString(){
        String text = scanner.nextLine();
        return text;
    }
    /**
     * Will ask user until a valid input is given
     * @param min
     * @param limit
     * @return
     */
    public int getInt(int min, int limit){
        int choice;
        String input;
        try{
            input = scanner.nextLine();
            if (input.matches("[0-9]+")) {
                choice =  Integer.parseInt(input);
                if(choice > min && choice < limit){
                    return choice;
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
        System.out.println("Invalid Input");
        System.out.print(":");
        return getInt(min, limit);
    }
}
