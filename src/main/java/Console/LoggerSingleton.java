package Console;

public class LoggerSingleton {

        private int currentLine = 0;
        private static LoggerSingleton logger = null;
        private ConsoleColors c;
        private LoggerSingleton() {
            c = new ConsoleColors();
        }

        public static LoggerSingleton getInstance() {
            if (logger == null) {
                logger = new LoggerSingleton();
            }
            return logger;
        }
        public void prompt(String message){
            System.out.print(c.GREEN + message + c.RESET );
        }
        public void error(String message){
            System.out.println(c.RED + message + c.RESET);
        }
        public void select(int index, String message){
            System.out.println(c.BLUE + " [ " + String.valueOf(index) + " ] " + message + c.RESET);
        }
        public void log(String message) {
            currentLine++;
            System.out.println(c.BLUE + "::" + c.RESET  + message);
        }
}
