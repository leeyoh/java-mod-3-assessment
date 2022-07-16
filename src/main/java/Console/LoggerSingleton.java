package Console;

public class LoggerSingleton {
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
            System.out.println(c.BLUE + "::" + c.RESET  + message);
        }
        public void message(String message) {
            System.out.println(c.RED + message + c.RESET);
        }

        public void clearScreen(){
            final int MAX_LINES = 50;
            for(int i = 0; i < MAX_LINES; i++){
                System.out.println("");
            }
        }
}
