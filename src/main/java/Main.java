import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Person test = new Person("John","Lee",1999,10,10);
        FileManagerSingleton fm = FileManagerSingleton.getInstance();
        List<Person> plist = new ArrayList<Person>();
        plist.add(test);
        fm.saveAsJSON("test.json",plist);
    }
}
