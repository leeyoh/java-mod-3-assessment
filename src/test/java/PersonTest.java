import Hosptial.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    Person p;
    @BeforeEach
    void setUp() {
        p  = new Person("Thomas","Jay");
    }
    @AfterEach
    void tearDown() {
    }
    @Test
    void getFirstName() {
        p.setFirstName("Joe");
        String actual = p.getFirstName();
        assertEquals("Joe",actual, "Testing for Base Case");
    }
    @Test
    void getLastName() {
        p.setLastName("Lee");
        String actual = p.getLastName();
        assertEquals("Lee",actual, "Testing for Base Case");
    }
}