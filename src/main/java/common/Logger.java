package common;

public class Logger {
    public static void LogException(Exception ex){
        System.out.println(ex.getMessage());
        System.out.println(ex.getCause().getMessage());

    }
}
