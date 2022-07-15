package Hosptial;

import java.util.List;

public class Doctor extends Person{

    private Specialty specialty;
    private final int MAX_PATIENT = 100;
    private int currentNumPatients = 0;
    private List<Patient> patients;

    public Doctor(String firstName, String lastName, Specialty special){
        super(firstName,lastName);
        this.specialty = special;
    }

    public Specialty getSpecialty(){
        return this.specialty;
    }

    public void increamentPatient(){
        this.currentNumPatients += 1;
    }

    public boolean canTakeMorePatients(){
        return this.currentNumPatients < this.MAX_PATIENT;
    }
    @Override
    public String toString(){
        return this.getFirstName() + this.getLastName();
    }
}
