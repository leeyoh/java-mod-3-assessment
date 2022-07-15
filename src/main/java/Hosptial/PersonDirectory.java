package Hosptial;

import FileManager.FileManagerSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonDirectory {
    private List<Person> personalList = new ArrayList<Person>();
    private List<Person> oldList = new ArrayList<Person>();

    private FileManagerSingleton fm;

    public PersonDirectory(){
        fm = FileManagerSingleton.getInstance();
    }
    public void saveToFile(String fileName){
        StringBuffer persons = new StringBuffer();
        personalList.forEach((person) ->{
//            persons.append(person.formatAsCSV());
            persons.append("\n");
        });
        fm.appendToFile(fileName,persons);
    }
    public void saveToFileJson(String fileName){
        List<Person> tempList = new ArrayList<Person>();
        tempList.addAll(oldList);
        tempList.addAll(personalList);
       // fm.saveAsJSON(fileName,tempList);
    }

    public void printDirectory(){
        oldList.forEach((person) ->{
            System.out.println(person);
        });
        personalList.forEach((person) ->{
            System.out.println(person);
        });
    }
    public void printPersonListAsJSON() {
        try{
            List<Person> tempList = new ArrayList<Person>();
            tempList.addAll(oldList);
            tempList.addAll(personalList);
            String json = new ObjectMapper().writeValueAsString(tempList);
            System.out.println(json);
        }catch(Exception e){
            System.out.println(e);
        }

    }
    public void loadDirectory(String fileName) throws IOException {
        try{
            List<String> tempList = new ArrayList<String>();
            tempList = fm.readFromFile(fileName);
//            tempList.forEach((csvPerson) -> {
//                oldList.add(new Person(csvPerson));
//            });
        }catch(Exception e){
            throw e;
        }
    }

    public void addPerson(String firstName, String lastName, int birthYear, int birthMonth, int birthDay){
        personalList.add(new Person(firstName,lastName));
    }



}
