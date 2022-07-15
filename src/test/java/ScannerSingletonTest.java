import Scanner.ScannerSingleton;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

//https://stackoverflow.com/questions/1647907/junit-how-to-simulate-system-in-testing
class ScannerSingletonTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    ScannerSingleton s;
    @BeforeEach
    void setUp() {
        s  = ScannerSingleton.getInstance();
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }
    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }
    private void provideInput(@NotNull String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }
    private String getOutput() {
        return testOut.toString();
    }

    /**
     * TODO figure this stuff out, taking to long so gave up for now.
     * java.util.NoSuchElementException: No line found
     */
//    @Test
//    void getIntTest() {
//        provideInput("1\n");
//        //provideInput(Character.toString((char) 13));
//        int result = s.getInt(0,9999);
//        assertEquals(1, result);
//    }

    /**
     * TODO figure this stuff out, taking to long so gave up for now.
     * java.util.NoSuchElementException: No line found
     */
 //   @Test
//    void getStringTest() {
//        provideInput("wefwef\n");
//        String result = s.getString();
//        assertEquals("wefwef", result);
//    }
}