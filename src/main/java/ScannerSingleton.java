import java.util.Locale;
import java.util.Scanner;

public class ScannerSingleton {
    private static ScannerSingleton scan = null;
    private Scanner scanner;

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
    public String getNextLine(){
        return scanner.nextLine();
    }
    public int getInt(int limit){
        int choice = 0;
        try{
            choice = scanner.nextInt();
            if(choice < 1 || choice > limit){
                return 0;
            }
        } catch (Exception e){
            System.out.println("Invalid Number");
        }
        scanner.nextLine();
        return choice;
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
