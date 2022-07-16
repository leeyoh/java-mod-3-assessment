import Console.LoggerSingleton;
import FileManager.FileManagerSingleton;
import Hosptial.*;
import Scanner.Prompt;
import Scanner.ScannerSingleton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.*;
import java.util.stream.IntStream;

public class Terminal {
    private static PersonDirectory pd;
    private static ScannerSingleton sc;
    private static LoggerSingleton log;
    private static FileManagerSingleton fm;
    private static String fileName = "list";
    private static final String path = "./data/";
    private static String filePath = path + fileName;
    private static Map<State, Prompt> prompts;
    private static List<Hospital> hospitals;
    private static final int TEXT_BASE = 1000;
    private static final int PROMPT_BASE = 2000;
    private final int INT_BASE = 0;
    private static Hospital currentHosptial;
    private static List<Patient> activePatients;
    private static List<Patient> waitListedPatients;

    public static void main(String[] args) {
        initializeObjects();
        createFolder();
        createQuestions();
        listFiles(path);
        loadWaitList();
        mainMenu();

        //loadFile();
        //chooseOptions();
    }
    public static void listFiles(String path) {
        try{
            fm.listFilesUsingJavaIO(path);
        }catch(Exception e){
            log.error(String.valueOf(e));
        }
    }
    /**
     * Values greater than 1000, the user is prompted for String
     * Values less than 1000, the user is prompted for INT
     * Value greater than 2000, is just a statement
     */
    public static void createQuestions(){
        prompts.put(State.MAIN_MENU,new Prompt(
                new HashMap<>() {{
                    put(1, "Treat Patients");
                    put(2, "New Hospital");
                    put(3, "New Patients");
                    put(4, "Exit");
                }}
        ));
        prompts.put(State.NEW_HOSPITAL,new Prompt(
                new HashMap<>() {{
                    put(TEXT_BASE + 1, "Hospital Name?");
                    put(1, "# of Doctors?");
                }}
        ));
        prompts.put(State.NEW_DOCTOR,new Prompt(
                new HashMap<>() {{
                    put(TEXT_BASE + 1, "Doctor First Name?");
                    put(TEXT_BASE + 2, "Doctor Last Name?");
                    put(PROMPT_BASE + 1, "Doctor Speciality");
                }}
        ));
        prompts.put(State.LIST_SPECIALTIES,new Prompt(
                new HashMap<>() {{
                    int i = 1;
                    for(Specialty speciality :Specialty.values()){
                        put(i,speciality.name());
                        i+=1;
                    }
                }}
        ));
        prompts.put(State.LIST_ALIMENTS,new Prompt(
                new HashMap<>() {{
                    int i = 1;
                    for(Aliment aliment :Aliment.values()){
                        put(i,aliment.name());
                        i+=1;
                    }
                }}
        ));
        prompts.put(State.NEW_PATIENT,new Prompt(
                new HashMap<>() {{
                    put(TEXT_BASE + 1, "Patient First Name?");
                    put(TEXT_BASE + 2, "Patient Last Name?");
                    put(PROMPT_BASE + 1, "Patient Aliment");
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
        waitListedPatients = new ArrayList<Patient>();
        activePatients = new ArrayList<Patient>();
    }
    private static void startHospital(){
        List<String> answers = prompts.get(State.NEW_HOSPITAL).askSequence(new int[]{TEXT_BASE + 1,1});
        currentHosptial = new Hospital(answers.get(0), Integer.parseInt(answers.get(1)));
        hospitals.add(currentHosptial);
    }
    private static void addDoctor(){
        List<String> answers = prompts.get(State.NEW_DOCTOR).askSequence(new int[]{TEXT_BASE + 1,TEXT_BASE + 2,PROMPT_BASE + 1});
        Specialty specialty = Specialty.values()[prompts.get(State.LIST_SPECIALTIES).chooseMulti(IntStream.rangeClosed(1,Specialty.values().length).toArray())-1];
        currentHosptial.addDoctor(new Doctor(answers.get(0),answers.get(1),specialty));
    }
    private static void addPatient(){
        List<String> answers = prompts.get(State.NEW_PATIENT).askSequence(new int[]{TEXT_BASE + 1,TEXT_BASE + 2,PROMPT_BASE + 1});
        Aliment aliment = Aliment.values()[prompts.get(State.LIST_ALIMENTS).chooseMulti(IntStream.rangeClosed(1,Aliment.values().length).toArray())-1];
        waitListedPatients.add(new Patient(answers.get(0), answers.get(1), aliment ));
    }
    private static void updateWaitList(){
        for(Patient p : waitListedPatients){
            for(Hospital hospital: hospitals){
                for(Doctor doc : hospital.getDoctors()){
                    if(doc.getSpecialty().equals(p.getAliment().getSpecialty())){
                        hospital.addPatient(p);
                        activePatients.add(p);
                    }
                }
            }
        }
        for(Patient p : activePatients){
            waitListedPatients.remove(p);
        }
    }
    private static void printWaistList(){
        log.log("Wait List: " + String.valueOf(waitListedPatients));
    }
    private static void printActiveList(){
        log.log("Active List: " + String.valueOf(activePatients));
    }
    /**
     * Main Menu for Terminal
     * TODO change array of ints to ENUMS
     */
    public static void mainMenu(){
        int[] options = new int[]{2,3,4};
        if(activePatients.size() > 0){
            options = new int[]{1,2, 3,4};
        }
        int choice = prompts.get(State.MAIN_MENU).chooseMulti(options);
        switch(choice){
            case 1: // Treatment
                //Add Person to List
                //addPerson();
                //chooseOptions();
                break;
            case 2: // New Hospital
                startHospital();
                for(int i = 0; i < currentHosptial.getNumDoctors(); i ++){
                    addDoctor();
                }
                currentHosptial.printHospital();
                updateWaitList();
                printActiveList();
                printWaistList();
                saveWaitList();
                break;
            case 3: // New Patient
                addPatient();
                updateWaitList();
                printActiveList();
                printWaistList();
                saveWaitList();
                //Append List to file then close program
                //pd.saveToFile(filePath + ".csv");
                //.saveToFileJson(filePath + ".json");
                break;
            case 4:
                exitTerminal("-----------");
                return;
            default:
                break;
        }
        mainMenu();
    }
    public static void loadWaitList() {
        try{
            waitListedPatients = (List<Patient>) fm.jsonFileToObject(path + "WaitList.json",waitListedPatients.getClass());
        }catch(Exception e){
            System.out.println(e);
        }
        printWaistList();
    }

    public static void saveWaitList(){
        fm.saveAsJSON(path + "WaitList.json",waitListedPatients);
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
