package Hosptial;
import Console.ConsoleColors;
import java.util.*;

public class Hospital {
    private int numPatients, numDoctors;
    private String name;
    private List<Doctor> doctors;
    private List<Patient> patients;
    private Map<Doctor, List<Patient>> patientChart;
    private Map<Specialty, List<Doctor>> docChart;
    private Map<Aliment,Specialty> symToSpecial;
    public Hospital(){}
    public Hospital( String name, int  numDoctors){
        this.numDoctors = numDoctors;
        this.numPatients = 0;
        this.name = name;
        this.doctors = new ArrayList<>();
        this.patients = new ArrayList<>();
        patientChart = new HashMap<>();
        docChart = new HashMap<>();
        symToSpecial = new HashMap<>();
        symToSpecial.put(Aliment.CANCER, Specialty.RADIOLOGY);
        symToSpecial.put(Aliment.CAVITY, Specialty.DERMATOLOGY);
        symToSpecial.put(Aliment.COLIC, Specialty.PEDIATRICS);
        symToSpecial.put(Aliment.RASH, Specialty.DERMATOLOGY);
    }
    @Override
    public String toString(){
        return "Hospital: " + this.name;
    }
    public String getName(){
        return this.name;
    }
    public List<Doctor> getDoctors(){
        return doctors;
    }
    public List<Patient> getPatients(){
        return patients;
    }
    public int addDoctor(Doctor doc){
        Specialty special = doc.getSpecialty();
        if(doctors.size() >= numDoctors){
            return 1;
        }
        this.doctors.add(doc);
        if(docChart.containsKey(special)){
            docChart.get(special).add(doc);
            return 0;
        }
        List<Doctor> doclist =  new ArrayList<Doctor>();
        doclist.add(doc);
        docChart.put(special, doclist);
        return 0;
    }
    public void removePatient(Patient pat){
        try{
            this.patients.remove(pat);
        }catch(Exception e){

        }

    }
    public void addPatient(Patient pat){
        this.patients.add(pat);
    }

    /**
     * Prints the current Hospitals Stats
     */
    public void printHospital(){
        System.out.print(ConsoleColors.GREEN_BACKGROUND);
        System.out.println("" + ConsoleColors.RESET);
        System.out.println("Hospital: " +
               ConsoleColors.CYAN_BACKGROUND + this.name + ConsoleColors.RESET +
                ConsoleColors.CYAN_BACKGROUND + Doctor.MAX_PATIENT + ConsoleColors.RESET + " # Docs : " +
                ConsoleColors.CYAN_BACKGROUND +  this.numDoctors + ConsoleColors.RESET + " # Patients : " +
                ConsoleColors.CYAN_BACKGROUND + this.numPatients + ConsoleColors.RESET);
        System.out.println("---------------------------");
        System.out.println("Map < Doctor , Patient > ");
        System.out.println(ConsoleColors.CYAN_BACKGROUND + patientChart + ConsoleColors.RESET);
        System.out.println("Map < Specialties , Doctors > ");
        System.out.println(ConsoleColors.CYAN_BACKGROUND + docChart + ConsoleColors.RESET);
    }
    public int getNumDoctors(){
        return this.numDoctors;
    }
}
