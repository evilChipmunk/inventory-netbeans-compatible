
import common.ScreenLoader;
import common.ValidationException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Thread.setDefaultUncaughtExceptionHandler(Main::showError);
        new ScreenLoader().load(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void showError(Thread t, Throwable e) {
        System.err.println("***Default exception handler***");
        if (Platform.isFxApplicationThread()) {
            showErrorDialog(e);
        } else {
            System.err.println("An unexpected error occurred in "+t);

        }
    }

    private static void showErrorDialog(Throwable e) {

        String errorMsg = "";
        try{
            InvocationTargetException ex = (InvocationTargetException) e.getCause();

            ValidationException vex = (ValidationException) ex.getTargetException();

            if (ex.getTargetException() instanceof ValidationException) {
                errorMsg = ex.getTargetException().getMessage();
                Alert validationAlert = new Alert(Alert.AlertType.WARNING, errorMsg, ButtonType.OK);
                validationAlert.showAndWait();
                return;
            }
            else {
                errorMsg = ex.getMessage();
                errorMsg += "\r\n" + e.getStackTrace().toString();
            }
        }
        catch (Exception exx){
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));

            errorMsg = e.getMessage();
            errorMsg += "\r\n" + e.getStackTrace().toString();
        }

        if (errorMsg.isEmpty()){
            errorMsg = "Error occurred";
        }

        Alert errorAlert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        errorAlert.showAndWait();

    }
}
