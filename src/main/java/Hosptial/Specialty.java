package Hosptial;

public enum Specialty {
        DERMATOLOGY (1),
        PEDIATRICS (2),
        RADIOLOGY (3),
        DENTIST (4);
        private final int index;
    Specialty(int index) {
        this.index = index;
    }
    int getIndex(){
            return index;
        }
}
