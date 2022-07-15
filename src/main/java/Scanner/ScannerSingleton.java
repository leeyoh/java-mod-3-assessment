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
    public String getMonth(){
        //https://stackoverflow.com/questions/2268969/convert-month-string-to-integer-in-java
        String input2 = scanner.nextLine().toLowerCase(Locale.ROOT);
        switch(input2) {
            case "1":
            case "january":
            case "jan":
                input2 = "1";
                break;
            case "2":
            case "febuary":
            case "feb":
                input2 = "2";
                break;
            case "3":
            case "march":
            case "mar":
                input2 = "3";
                break;
            case "4":
            case "april":
            case "apr":
                input2 = "4";
                break;
            case "5":
            case "may":
                input2 = "5";
                break;
            case "6":
            case "june":
            case "jun":
                input2 = "6";
                break;
            case "7":
            case "july":
            case "jul":
                input2 = "7";
                break;
            case "8":
            case "august":
            case "aug":
                input2 = "8";
                break;
            case "9":
            case "september":
            case "sep":
            case "sept":
                input2 = "9";
                break;
            case "10":
            case "october":
            case "oct":
                input2 = "10";
                break;
            case "11":
            case "november":
            case "nov":
                input2 = "11";
                break;
            case "12":
            case "december":
            case "dec":
                input2 = "12";
                break;
            default:
                System.out.println("Invalid Month");
                input2 = "0";
        }
        return input2;
    }
}
