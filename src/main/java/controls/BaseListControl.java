package controls;

import common.*;
import controllers.BaseController;
import domain.BaseEntity;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import viewmodels.BaseViewModel;

import java.io.IOException;

public abstract class BaseListControl<TModel extends BaseEntity, TViewModel extends BaseViewModel> extends VBox {

    private TextField searchTextBox;
    private Button deleteButton;
    private Button modifyButton;
    private TableView tblItems;


    protected abstract String getFxmlPath();
    protected abstract String getAddPath();
    protected abstract String getModifyPath();

    protected abstract BaseController<TViewModel> getBaseController();
    protected abstract BaseController<TViewModel> getBaseController(TModel model);
    protected abstract void deleteAction(TModel item);
    protected abstract ObservableCollection<TModel> getListFromStore();
    protected abstract TModel findItemById(int id);


    protected abstract TableView getTable();
    protected abstract Button getDeleteButton();
    protected abstract Button getModifyButton();
    protected abstract TextField getSearchText();


    public BaseListControl() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(getFxmlPath()));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void initialize() {

        //get controls, @FXML doesn't seem to inject into base class. Possibly because they are private
        tblItems = getTable();
        deleteButton = getDeleteButton();
        modifyButton = getModifyButton();
        searchTextBox = getSearchText();

        //Set the table to multi selection mode
        tblItems.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Set the table's data
        setTableDataSource();

        //by default disable the delete/modify buttons
        setButtonsDisabled(true);

        /**A listener for list selections, multiple selections in the TableView**/
        setSelectionListener();

        setTableRowDoubleClick();
    }

    private void setButtonsDisabled(boolean disabled) {
        deleteButton.setDisable(disabled);
        modifyButton.setDisable(disabled);
    }

    private void setSelectionListener(){
        ListChangeListener< TModel> multiSelection = new ListChangeListener<TModel>(){
            @Override
            public void onChanged(  ListChangeListener.Change<? extends TModel> changed){
                setButtonsDisabled(changed.getList().size() <= 0);
            }
        };
        tblItems.getSelectionModel().getSelectedItems().addListener(multiSelection);

    }

    @FXML
    public void navAdd(ActionEvent actionEvent) {

        try {
            BaseController<TViewModel> controller = getBaseController();
            ScreenLoader loader = new ScreenLoader();
            loader.load(actionEvent, getAddPath(), controller);
        } catch (Exception ex) {
            Logger.LogException(ex);
        }
    }

    @FXML
    public void navModify(ActionEvent actionEvent) {
        try {
            TModel item = getSelectedItem();
            BaseController<TViewModel> controller = getBaseController(item);

            ScreenLoader loader = new ScreenLoader();
            loader.load(actionEvent, getModifyPath(), controller);
        } catch (Exception ex) {
            Logger.LogException(ex);
        }
    }

    protected String getDeleteFailureReason(){
        return "Unable to delete item";
    }

    @FXML
    public void deleteItem(ActionEvent actionEvent) {

        if (!canDelete()){
            Alert deleteAlert = new Alert(Alert.AlertType.WARNING, getDeleteFailureReason());
            deleteAlert.showAndWait();
            return;
        }

        TModel item = getSelectedItem();
        String message = "Are you sure you want to delete item with id : " + item.getId() + " ?";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            deleteAction(item);
            setTableDataSource();
        }
    }

    @FXML
    public void search(ActionEvent event) {
        String searchText = searchTextBox.getText();
        if (searchText == null || searchText.isEmpty()) {
            return;
        }

        int id;
        try {
            id = Integer.parseInt(searchTextBox.getText());
        } catch (Exception e) {
            throw new ValidationException("search criteria must be a valid integer based id");
        }

        TModel item = findItemById(id);
        ObservableCollection<TModel> items = new ObservableCollection<TModel>();
        items.add(item);
        setTableDataSource(items);
    }

    @FXML
    public void searchChanged(KeyEvent e) {
        String searchText = searchTextBox.getText();

        if (searchText == null || searchText.isEmpty()) {
            setTableDataSource();
        }
    }

    protected boolean canDelete(){
        return true;
    }

    private void setTableDataSource(ObservableCollection<TModel> items) {
        tblItems.setItems(items.getList());
    }

    private void setTableDataSource() {
        ObservableCollection<TModel> items = getListFromStore();
        tblItems.setItems(items.getList());
    }

    public boolean getHasSelection() {
        return tblItems.getSelectionModel().getSelectedItems().size() == 1;
    }

    protected TModel getSelectedItem(){
        return (TModel)  tblItems.getSelectionModel().getSelectedItem();
    }

    private void setTableRowDoubleClick(){

        getTable().setRowFactory( tv -> {
            TableRow<TModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    TModel rowData = row.getItem();
                    ActionEvent ev = new ActionEvent(event.getSource(), event.getTarget());

                    navModify(ev);
                    System.out.println(rowData);
                }
            });
            return row;
        });
    }
}