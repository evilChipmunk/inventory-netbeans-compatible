package controls;


import common.Configuration;
import common.ObservableCollection;
import domain.parts.Part;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class PartTableControl  extends VBox {

    @FXML
    private TableView tblParts;

    private SimpleBooleanProperty hasSelection;


    public PartTableControl() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Configuration.Paths.get_TablePartPath()));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        hasSelection = new SimpleBooleanProperty(false);
    }

    public TableView getTblParts() {
        return tblParts;
    }

    public void initialize() {

        //Set the table to multi selection mode
        tblParts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ListChangeListener<Part> selectionListener = changed -> hasSelection.set(changed.getList().size() > 0);
        tblParts.getSelectionModel().getSelectedItems().addListener(selectionListener);

    }

    public SimpleBooleanProperty getHasSelection() {
        return hasSelection;
    }

    public Part getSelectedPart() {
        return (Part) tblParts.getSelectionModel().getSelectedItem();
    }

    public void setTableDataSource(ObservableCollection<Part> parts) {
        tblParts.setItems(parts.getList());
    }
}
