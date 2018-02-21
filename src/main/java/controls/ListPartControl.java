package controls;

import common.*;
import controllers.BaseController;
import controllers.parts.PartController;
import domain.parts.Part;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import viewmodels.PartViewModel;

public class ListPartControl   extends BaseListControl<Part, PartViewModel> {
    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnModify;

    @FXML
    private GridPane hbox;

    @FXML
    private PartTableControl tblParts;



    @Override
    protected String getFxmlPath() {
        return Configuration.Paths.get_ListPartPath();
    }

    @Override
    protected String getAddPath() {
        return Configuration.Paths.get_AddPartPath();
    }

    @Override
    protected String getModifyPath() {
        return Configuration.Paths.get_ModifyPartPath();
    }

    @Override
    protected BaseController<PartViewModel> getBaseController() {
        return  new PartController();
    }

    @Override
    protected BaseController<PartViewModel> getBaseController(Part model) {
        return new PartController(model);
    }

    @Override
    protected void deleteAction(Part item) {
        PartDataStore store = PartDataStore.getInstance();
        store.removePart(item);
    }

    @Override
    protected ObservableCollection<Part> getListFromStore() {
        PartDataStore store = PartDataStore.getInstance();
        ObservableCollection<Part> parts = store.getParts();
        return parts;
    }

    @Override
    protected Part findItemById(int id) {
        PartDataStore store = PartDataStore.getInstance();
        return store.findPart(id);
    }

    @Override
    protected TableView getTable() {
        return tblParts.getTblParts();
    }

    @Override
    protected Button getDeleteButton() {
        return btnDelete;
    }

    @Override
    protected Button getModifyButton() {
        return btnModify;
    }

    @Override
    protected TextField getSearchText() {
        return txtSearch;
    }

}
