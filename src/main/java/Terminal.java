import Console.LoggerSingleton;
import FileManager.FileManagerSingleton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

public class Terminal {
    private static PersonDirectory pd;
    private static ScannerSingleton sc;
    private static LoggerSingleton log;
    private static FileManagerSingleton fm;

    private static String fileName = "list";
    private static final String path = "data/";
    private static String filePath = path + fileName;

    public static void main(String[] args) {
        initializeObjects();
        //loadFile();
        //chooseOptions();
        createFolder();

    }
    public static void createFolder(){
        try{
            fm.createFolder("data");
        } catch (Exception e){
            System.out.println(e);
            exitTerminal("Failed to Create Folder");
        }
    }
    public static void exitTerminal(String message){
        log.error(message);
        if(sc != null) {
            sc.close();
        }
    }
    public static void initializeObjects(){
        pd = new PersonDirectory();
        sc = ScannerSingleton.getInstance();
        log = LoggerSingleton.getInstance();
        fm = FileManagerSingleton.getInstance();
    }

    public static void mainMenu(){
        log.log(" [ 1 ] Add a person to the list");
        log.log(" [ 2 ] Print a list of current persons");
        log.log(" [ 3 ] Exit the program");
        log.prompt(":");
        switch(sc.getInt(3)){
            case 1:
                //Add Person to List
                addPerson();
                //chooseOptions();
                break;
            case 2:
                //List the current list of people, old + new
                printOption();
                //chooseOptions();
                break;
            case 3:
                //Append List to file then close program
                pd.saveToFile(filePath + ".csv");
                pd.saveToFileJson(filePath + ".json");
                break;
            default:
                //chooseOptions();
                break;
        }
    }
    public static void printOption(){
        log.log(" [1] CSV");
        log.log(" [2] JSON");
        log.prompt(": ");
        switch(sc.getInt(2)){
            case 2:
                pd.printPersonListAsJSON();
                break;
            default:
                pd.printDirectory();
                break;
        }
    }
    /**
     * First Name ( String )
     * Last Name
     * Birth Year
     * Birth Month
     * Birth Day
     */
    public static void addPerson(){
        String firstName,lastName,year,month,day;

        log.prompt("First Name: ");
        firstName = sc.getNextLine();

        log.prompt("Last Name: ");
        lastName = sc.getNextLine();

        log.log("BirthDay");
        year = getYear();
        month = getMonth();
        day = getDay(year,month);

        pd.addPerson(firstName,lastName,
                Integer.valueOf(year),
                Integer.valueOf(month),
                Integer.valueOf(day));
    }
    public static String getYear(){
        log.prompt("Year: ");
        int year = sc.getInt(9999);
        if(year < 1){
            getYear();
        }
        return String.valueOf(year);
    }

    public static String getMonth(){
        log.prompt("Month: ");
        String month = sc.getMonth();
        if(month == "0"){
            getMonth();
        }
        return month;
    }

    public static String getDay(String year, String month){
        log.prompt("Day: ");
        String day = String.valueOf(sc.getInt(31));

        if(!isValid(year + '-' + month + "-" + day)){
            log.error("Invalid Day");
            getDay(year,month);
        }
        return day;
    }
    //https://mkyong.com/java/how-to-check-if-date-is-valid-in-java/
    public static boolean isValid(final String date) {
        boolean valid = false;
        try {
            // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
            LocalDate.parse(date,
                    DateTimeFormatter.ofPattern("uuuu-M-d")
                            .withResolverStyle(ResolverStyle.STRICT)
            );
            valid = true;
        } catch (DateTimeParseException e) {
            //e.printStackTrace();
            valid = false;
        }
        return valid;
    }

    public static void loadPersonal(){
        try{
            pd.loadDirectory(filePath+ ".csv");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static void loadFile(){
        try{
            log.log("Files in Directory:");
            List<String> files = fm.listFilesUsingJavaIO("data");
            if(files.isEmpty()){
                log.log("Created New File: " + filePath + ".csv");
                fm.createFile(filePath + ".csv");
            } else {
                fileName = files.get(0);
                log.log("Loaded " + filePath+ ".csv");
                loadPersonal();
            }
        } catch(Exception e){
            System.out.println(e);
        }
    }
    public static void clearScreen(){
        final int MAX_LINES = 50;
        for(int i = 0; i < MAX_LINES; i++){
            log.log("-");
        }
    }
}
