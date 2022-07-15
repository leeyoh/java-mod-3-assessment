import Console.LoggerSingleton;
import FileManager.FileManagerSingleton;
import Hosptial.Hospital;
import Hosptial.PersonDirectory;
import Scanner.Prompt;
import Scanner.ScannerSingleton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.*;

public class Terminal {
    private static PersonDirectory pd;
    private static ScannerSingleton sc;
    private static LoggerSingleton log;
    private static FileManagerSingleton fm;
    private static String fileName = "list";
    private static final String path = "data/";
    private static String filePath = path + fileName;
    private static Map<State, Prompt> prompts;
    private static List<Hospital> hospitals;
    public static void main(String[] args) {
        initializeObjects();
        createFolder();
        createQuestions();
        mainMenu();

        //loadFile();
        //chooseOptions();
    }

    /**
     * Values greater than 1000, the user is prompted for String
     * Values less than 1000, the user is prompted for INT
     */
    public static void createQuestions(){
        prompts.put(State.MAIN_MENU,new Prompt(
                new HashMap<>() {{
                    put(1, "Treat Patients");
                    put(2, "New Hospital");
                    put(3, "New Patients");
                }}
        ));
        prompts.put(State.NEW_HOSPITAL,new Prompt(
                new HashMap<>() {{
                    put(1001, "Hospital Name?");
                    put(2, "# of Doctors?");
                }}
        ));
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
        hospitals = new ArrayList<Hospital>();
        prompts = new HashMap<>();
        pd = new PersonDirectory();
        sc = ScannerSingleton.getInstance();
        log = LoggerSingleton.getInstance();
        fm = FileManagerSingleton.getInstance();
    }
    private static void startHospital(){
        List<String> answers = prompts.get(State.NEW_HOSPITAL).askSequence(new int[]{1001,2});
        System.out.println(answers.get(0));
        System.out.println(answers.get(1));
        hospitals.add(
                new Hospital(answers.get(0),
                        Integer.parseInt(answers.get(1))
                )
        );
    }
    /**
     * Main Menu for Terminal
     * TODO change array of ints to ENUMS
     */
    public static void mainMenu(){
        int choice = prompts.get(State.MAIN_MENU).chooseMulti(new int[]{1,2, 3});
        switch(choice){
            case 1: // Treatment
                //Add Person to List
                //addPerson();
                //chooseOptions();
                break;
            case 2: // New Hospital
                startHospital();
                break;
            case 3: // New Patient
                //Append List to file then close program
                //pd.saveToFile(filePath + ".csv");
                //.saveToFileJson(filePath + ".json");
                break;
            default:
                break;
        }
        mainMenu();
    }
    public static void printOption(){
        log.log(" [1] CSV");
        log.log(" [2] JSON");
        log.prompt(": ");
        switch(sc.getInt(0,2)){
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
//    public static void addPerson(){
//        String firstName,lastName,year,month,day;
//
////        log.prompt("First Name: ");
////        firstName = sc.getNextLine();
////
////        log.prompt("Last Name: ");
////        lastName = sc.getNextLine();
//
//        log.log("BirthDay");
//        year = getYear();
//        month = getMonth();
//        day = getDay(year,month);
//
//        pd.addPerson(firstName,lastName,
//                Integer.valueOf(year),
//                Integer.valueOf(month),
//                Integer.valueOf(day));
//    }
    public static String getYear(){
        log.prompt("Year: ");
        int year = sc.getInt(0,9999);
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
        String day = String.valueOf(sc.getInt(0,31));

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
