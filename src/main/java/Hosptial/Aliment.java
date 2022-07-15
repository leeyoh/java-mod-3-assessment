package Hosptial;
/**
 * a (1) treatment is considered 10 health points.
 */
public enum Aliment {
    RASH (2, Specialty.DERMATOLOGY),
    COLIC (4, Specialty.PEDIATRICS),
    CANCER (3, Specialty.RADIOLOGY),
    CAVITY (4, Specialty.DENTIST);
    private final int treatments;
    private final Specialty specialty;
    Aliment(int i, Specialty s){
        specialty = s;
        treatments = i;
    }
    int getTreatments(){
        return treatments;
    }
    Specialty getSpecialty(){ return specialty;}
}
