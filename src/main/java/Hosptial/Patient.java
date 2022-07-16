package Hosptial;

public class Patient extends Person{
    private Aliment aliment;
    private int healthBar;
    public Patient(){
        super();
    }
    public Patient(String firstName, String lastName, Aliment aliment){
        super(firstName, lastName);
        this.aliment = aliment;
        this.healthBar = 100 - (aliment.getTreatments() * 10);
    }
    /**
     * if the aliment isn't curable, do nothing.
     * If the patient is fully healed remove the aliment
     */
    public void treatPatient(){
        if(this.aliment.getCurable()){
            this.healthBar += 10;
            if(this.healthBar >= 100){
                this.healthBar = 100;
                this.aliment = null;
            }
        }
    }
    public Aliment getAliment(){
        return this.aliment;
    }
    @Override
    public String toString(){
        return this.getFirstName() + this.getLastName();
    }
}
