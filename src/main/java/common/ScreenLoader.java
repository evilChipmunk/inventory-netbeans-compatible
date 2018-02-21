package common;

import controllers.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;



import java.awt.*;
import java.io.IOException;

public class ScreenLoader {

    public void load(Stage primaryStage)  {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(Configuration.Paths.get_MainPath()));

            Dimension size = getDimension(WindowSize.Large);
            Scene scene = new Scene(fxmlLoader.load(), size.getWidth(), size.getHeight());
            scene.getStylesheets().add(getClass().getResource(Configuration.Paths.get_MainCss()).toExternalForm());

            primaryStage.setTitle("Inventory Management System");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException ex){
            Logger.LogException(ex);
        }
    }


    public void load(ActionEvent actionEvent, String path, WindowSize windowSize) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(path));

            Dimension size = getDimension(windowSize);
            Scene scene = new Scene(fxmlLoader.load(), size.getWidth(), size.getHeight());
            scene.getStylesheets().add(getClass().getResource(Configuration.Paths.get_MainCss()).toExternalForm());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
        catch (IOException ex){
            Logger.LogException(ex);
        }
    }

    public void load(ActionEvent actionEvent, String path, BaseController controller){

        WindowSize size = get_Size(path);
        load(actionEvent, path, size, controller);
    }

    public void load(ActionEvent actionEvent, String path, WindowSize windowSize, BaseController controller)   {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(path));
            fxmlLoader.setController(controller);

            fxmlLoader.getNamespace().put(
                    "model",
                    controller.model
            );

            Dimension size = getDimension(windowSize);
            Scene scene = new Scene(fxmlLoader.load(), size.getWidth(), size.getHeight());
            scene.getStylesheets().add(getClass().getResource(Configuration.Paths.get_MainCss()).toExternalForm());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

        } catch (IOException ex) {
            Logger.LogException(ex);
        }
    }

    private WindowSize get_Size(String path){
        if (path == Configuration.Paths.get_MainPath()){
            return WindowSize.Large;
        }

        if (path == Configuration.Paths.get_AddPartPath()){
            return WindowSize.Small;
        }

        if (path == Configuration.Paths.get_ModifyPartPath()){
            return WindowSize.Small;
        }



        if (path == Configuration.Paths.get_AddProductPath()){
            return WindowSize.Large;
        }

        if (path == Configuration.Paths.get_ModifyProductPath()){
            return WindowSize.Large;
        }
        return  WindowSize.Large;
    }

    private Dimension getDimension(WindowSize size){
        switch (size){
            case Small:
                return new Dimension(Configuration.WindowDimensions.get_SmallWindowWidth(), Configuration.WindowDimensions.get_StandardWindowHeight());
            case Medium:
                return new Dimension(Configuration.WindowDimensions.get_SmallWindowWidth(), Configuration.WindowDimensions.get_StandardWindowHeight());
            case Large:
                return new Dimension(Configuration.WindowDimensions.get_LargeWindowWidth(), Configuration.WindowDimensions.get_LargeWindowHeight());
                default:
                    return new Dimension();
        }
    }
}
