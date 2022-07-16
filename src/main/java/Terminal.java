import Console.LoggerSingleton;
import FileManager.FileManagerSingleton;
import Hosptial.*;
import Scanner.Prompt;
import Scanner.ScannerSingleton;
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

        loadWaitList();
        loadHospitals();
        initActivePatients();
        updateWaitList();
        printActiveList();
        printWaistList();

        createQuestions();
        mainMenu();
    }
    public static void listFiles(String path) {
        try {
            fm.listFilesUsingJavaIO(path);
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }
    /**
     * Values greater than 1000, the user is prompted for String
     * Values less than 1000, the user is prompted for INT
     * Value greater than 2000, is just a statement
     */
    public static void createQuestions() {
        prompts.put(State.MAIN_MENU, new Prompt(
                new HashMap<>() {{
                    put(1, "Treat Patients");
                    put(2, "New Hospital");
                    put(3, "New Patients");
                    put(4, "Exit");
                }}
        ));
        prompts.put(State.NEW_HOSPITAL, new Prompt(
                new HashMap<>() {{
                    put(TEXT_BASE + 1, "Hospital Name?");
                    put(1, "# of Doctors?");
                }}
        ));
        prompts.put(State.NEW_DOCTOR, new Prompt(
                new HashMap<>() {{
                    put(TEXT_BASE + 1, "Doctor First Name?");
                    put(TEXT_BASE + 2, "Doctor Last Name?");
                    put(PROMPT_BASE + 1, "Doctor Speciality");
                }}
        ));
        prompts.put(State.LIST_SPECIALTIES, new Prompt(
                new HashMap<>() {{
                    int i = 1;
                    for (Specialty speciality : Specialty.values()) {
                        put(i, speciality.name());
                        i += 1;
                    }
                }}
        ));
        prompts.put(State.LIST_ALIMENTS, new Prompt(
                new HashMap<>() {{
                    int i = 1;
                    for (Aliment aliment : Aliment.values()) {
                        put(i, aliment.name());
                        i += 1;
                    }
                }}
        ));
        prompts.put(State.NEW_PATIENT, new Prompt(
                new HashMap<>() {{
                    put(TEXT_BASE + 1, "Patient First Name?");
                    put(TEXT_BASE + 2, "Patient Last Name?");
                    put(PROMPT_BASE + 1, "Patient Aliment");
                }}
        ));
        prompts.put(State.TREAT_PATIENTS, new Prompt(
                new HashMap<>() {{
                    put(PROMPT_BASE + 1, "Treat Patients");
                    put(1, "Treat All");
                    put(2, "Return");
                    int i = 3;
                    for (Patient patient : activePatients) {
                        put(i, patient.getFirstName());
                        i += 1;
                    }
                }}
        ));
    }

    public static void createFolder() {
        try {
            fm.createFolder("data");
            fm.createFolder("data/hospitals");
        } catch (Exception e) {
            System.out.println(e);
            exitTerminal("Failed to Create Folder");
        }
    }
    public static void exitTerminal(String message) {
        log.error(message);
        if (sc != null) {
            sc.close();
        }
    }
    public static void initializeObjects() {
        hospitals = new ArrayList<Hospital>();
        prompts = new HashMap<>();
        pd = new PersonDirectory();
        sc = ScannerSingleton.getInstance();
        log = LoggerSingleton.getInstance();
        fm = FileManagerSingleton.getInstance();
        waitListedPatients = new ArrayList<Patient>();
        activePatients = new ArrayList<Patient>();
    }
    private static void treatPatient() {
        int[] options = new int[activePatients.size() + 3];
        options[0] = PROMPT_BASE + 1;
        options[1] = 1;
        options[2] = 2;
        for (int i = 3; i < activePatients.size() + 3; i++) {
            options[i] = i;
        }
        int choice = prompts.get(State.TREAT_PATIENTS).chooseMulti(options);
    }
    private static void startHospital() {
        List<String> answers = prompts.get(State.NEW_HOSPITAL).askSequence(new int[]{TEXT_BASE + 1, 1});
        currentHosptial = new Hospital(answers.get(0), Integer.parseInt(answers.get(1)));
        hospitals.add(currentHosptial);
    }
    private static void addDoctor() {
        List<String> answers = prompts.get(State.NEW_DOCTOR).askSequence(new int[]{TEXT_BASE + 1, TEXT_BASE + 2, PROMPT_BASE + 1});
        Specialty specialty = Specialty.values()[prompts.get(State.LIST_SPECIALTIES).chooseMulti(IntStream.rangeClosed(1, Specialty.values().length).toArray()) - 1];
        currentHosptial.addDoctor(new Doctor(answers.get(0), answers.get(1), specialty));
    }
    private static void addPatient() {
        List<String> answers = prompts.get(State.NEW_PATIENT).askSequence(new int[]{TEXT_BASE + 1, TEXT_BASE + 2, PROMPT_BASE + 1});
        Aliment aliment = Aliment.values()[prompts.get(State.LIST_ALIMENTS).chooseMulti(IntStream.rangeClosed(1, Aliment.values().length).toArray()) - 1];
        waitListedPatients.add(new Patient(answers.get(0), answers.get(1), aliment));
    }
    /**
     * TODO add error handling for misconfigured JSON
     */
    private static void initActivePatients() {
        for (Hospital hospital : hospitals) {
            activePatients.addAll(hospital.getPatients());
        }
    }
    private static void updateWaitList() {
        for (Patient p : waitListedPatients) {
            for (Hospital hospital : hospitals) {
                for (Doctor doc : hospital.getDoctors()) {
                    if (doc.getSpecialty().equals(p.getAliment().getSpecialty())) {
                        hospital.addPatient(p);
                        activePatients.add(p);
                    }
                }
            }
        }
        for (Patient p : activePatients) {
            waitListedPatients.remove(p);
        }
    }
    private static void printWaistList() {
        log.log("Wait List: " + String.valueOf(waitListedPatients));
    }
    private static void printActiveList() {
        log.log("Active List: " + String.valueOf(activePatients));
    }
    public static void updateList() {
        updateWaitList();
        saveWaitList();
        saveHosptials();
        printActiveList();
        printWaistList();
    }
    /**
     * Main Menu for Terminal
     * TODO change array of ints to ENUMS
     */
    public static void mainMenu() {
        int[] options = new int[]{2, 3, 4};
        if (activePatients.size() > 0) {
            options = new int[]{1, 2, 3, 4};
        }
        int choice = prompts.get(State.MAIN_MENU).chooseMulti(options);
        switch (choice) {
            case 1: // Treatment
                treatPatient();
                break;
            case 2: // New Hospital
                startHospital();
                for (int i = 0; i < currentHosptial.getNumDoctors(); i++) {
                    addDoctor();
                }
                currentHosptial.printHospital();
                updateList();
                break;
            case 3: // New Patient
                addPatient();
                updateList();
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
        try {
            waitListedPatients = fm.jsonFileToObjectList(path + "WaitList.json", Patient.class);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void loadHospitals() {
        try {
            for (String p : fm.listFilesUsingJavaIO(path + "hospitals/")) {
                System.out.println(p);
                hospitals.add(fm.jsonFileToObject(p, Hospital.class));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void saveHosptials() {
        for (Hospital h : hospitals) {
            fm.saveAsJSON(path + "hospitals/" + h.getName() + ".json", h);
        }
    }

    public static void saveWaitList() {
        fm.saveAsJSON(path + "WaitList.json", waitListedPatients);
    }
}

