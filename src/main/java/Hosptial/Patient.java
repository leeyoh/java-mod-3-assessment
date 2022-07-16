package Hosptial;

import java.util.Random;

public class Patient extends Person{
    private Aliment aliment;
    private int healthBar;
    /**
     * Default Constructor because Jackson is stupid
     * https://stackoverflow.com/questions/52708773/cannot-deserialize-from-object-value-no-delegate-or-property-based-creator-ev
     */
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
            switch(new Random().nextInt(10)){
                case 0:
                    this.healthBar -= 10;
                    break;
                case 1:
                    break;
                default:
                    if(this.aliment.getCurable()) {
                        this.healthBar += 10;
                    }
                    break;
            }
            if(this.healthBar >= 100){
                this.healthBar = 100;
            }
            if(this.healthBar <= 0){
                this.healthBar = 0;
            }
    }
    public void displayHealthBar(){
        String anim= "=";
        if(this.aliment.getCurable()){
            System.out.print(this.toString() +" " + this.aliment+ " HP: " + this.getHealthBar() + " ");
        }else{
            System.out.print(this.toString() +" " + this.aliment+ " HP: " + this.getHealthBar() + " NOT CURABLE" + " ");
        }
        for (int x = 0; x < this.getHealthBar(); x += 10) {
            System.out.print(anim);
        }
        System.out.println("");
    }
    public int getHealthBar(){
        return this.healthBar;
    }
    public Aliment getAliment(){
        return this.aliment;
    }
    @Override
    public String toString(){
        return this.hashCode() + ": " + this.getFirstName() + " " + this.getLastName();
    }
}
