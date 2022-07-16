package Hosptial;

import java.util.List;

public class Doctor extends Person{
    private Specialty specialty;
    public static final int MAX_PATIENT = 100;
    private int currentNumPatients = 0;
    /**
     * Default Constructor because Jackson is stupid
     * https://stackoverflow.com/questions/52708773/cannot-deserialize-from-object-value-no-delegate-or-property-based-creator-ev
     */
    public Doctor(){
        super();
    }
    public Doctor(String firstName, String lastName, Specialty special){
        super(firstName,lastName);
        this.specialty = special;
    }
    public Specialty getSpecialty(){
        return this.specialty;
    }
    @Override
    public String toString(){
        return this.getFirstName() + this.getLastName();
    }
}
