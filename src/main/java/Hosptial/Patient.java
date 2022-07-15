package Hosptial;

public class Patient extends Person{

    private Aliment aliment;

    public Patient(String firstName, String lastName, Aliment aliment){
        super(firstName, lastName);
        this.aliment = aliment;
    }

    public Aliment getSymptoms(){
        return this.aliment;
    }

    @Override
    public String toString(){
        return this.getFirstName() + this.getLastName();
    }
}
