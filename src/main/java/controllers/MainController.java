package controllers;

        import javafx.application.Platform;
        import javafx.event.ActionEvent;

public class MainController {

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }
}
