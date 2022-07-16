package Hosptial;
/**
 * a (1) treatment is considered 10 health points.
 */
public enum Aliment {
    RASH (2, false,  Specialty.DERMATOLOGY),
    COLIC (4, true,  Specialty.PEDIATRICS),
    CANCER (3, true,  Specialty.RADIOLOGY),
    CAVITY (4,true,  Specialty.DENTIST);
    private final int treatments;
    private final boolean curable;
    private final Specialty specialty;
    Aliment(int i, boolean c, Specialty s){
        specialty = s;
        treatments = i;
        curable = c;
    }
    public int getTreatments(){
        return treatments;
    }
    public boolean getCurable() {return curable;}
    public Specialty getSpecialty(){ return specialty;}
}
