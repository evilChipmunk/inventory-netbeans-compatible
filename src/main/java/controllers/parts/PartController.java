package controllers.parts;

import common.*;
import controllers.AddEditController;
import domain.parts.Part;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;
import viewmodels.PartViewModel;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


public class PartController extends AddEditController<PartViewModel, Part> {

    public PartController(){
        model = new PartViewModel();
    }

    public PartController(Part part){
        model = new PartViewModel(part);
    }

    @FXML
    private Label lblId;
    @FXML
    private Label lblTitle;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtInStock;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtMin;
    @FXML
    private TextField txtMax;
    @FXML
    private Label lblMachineId;
    @FXML
    private TextField txtMachineId;
    @FXML
    private Label lblCompanyName;
    @FXML
    private TextField txtCompanyName;
    @FXML
    private RadioButton rbInHouse;
    @FXML
    private RadioButton rbOutSourced;

    private ToggleGroup tgSource;


    @FXML
    public void initialize() {

        lblId.setText(model.getId().get());
        lblTitle.setText(model.getTitle().get());

        setBindings();

        setToggleBinding();

        setSubClassSpecicPropertyVisibility();

        setNumberFormat(txtInStock);
        setNumberFormat(txtMin);
        setNumberFormat(txtMax);
        setNumberFormat(txtMachineId);

        setCurrencyTextBox(txtPrice, getModelItem().getPrice());
        setMoneyValidation(txtPrice);
    }

    private void setBindings() {
        txtName.textProperty().bindBidirectional(model.getName());
        txtInStock.textProperty().bindBidirectional(model.getInStock(), new NumberStringConverter());
//        txtPrice.textProperty().bindBidirectional(model.getPrice()
//                , new NumberStringConverter(new DecimalFormat(DecimalFormatSymbols.getInstance().getCurrency().getSymbol())));
        txtMin.textProperty().bindBidirectional(model.getMin(), new NumberStringConverter(new DecimalFormat("#")));
        txtMax.textProperty().bindBidirectional(model.getMax(), new NumberStringConverter(new DecimalFormat("#")));
        txtMachineId.textProperty().bindBidirectional(model.getMachineId(), new NumberStringConverter(new DecimalFormat("#")));
        txtCompanyName.textProperty().bindBidirectional(model.getCompanyName());
    }

    private void setToggleBinding() {
        tgSource = new ToggleGroup();
        rbInHouse.setToggleGroup(tgSource);
        rbOutSourced.setToggleGroup(tgSource);
        SimpleBooleanProperty isInHouseProperty = model.getIsInHouse();

        rbInHouse.selectedProperty().bindBidirectional(isInHouseProperty);

        //binding radio buttons is always annoying. Notice the outsurced is set to NOT isInHouse
        //initial binding with toggle group isn't triggering the outsoure to be selected
        //if inhouse is set to false. Maybe there's a better way possible with another binding option
        rbOutSourced.setSelected(!isInHouseProperty.get());

        tgSource.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (tgSource.getSelectedToggle() != null) {
                    setSubClassSpecicPropertyVisibility();
                }
            }
        });
    }

    private void setSubClassSpecicPropertyVisibility(){

        //the binding action doesn't seem to update
        //from the visual stack in a reliable way here
        //both binding and change listener for selection
        //are being kicked off by an event, the change
        //occuring first so easier just to get the value
        //straight from the radio button
       // boolean isInHouse = model.getIsInHouse().get();

        boolean isInHouse = rbInHouse.isSelected();

        lblCompanyName.setVisible(!isInHouse);
        txtCompanyName.setVisible(!isInHouse);
        lblMachineId.setVisible(isInHouse);
        txtMachineId.setVisible(isInHouse);
    }

    @Override
    protected void saveAction(Part item) {

        PartDataStore store = PartDataStore.getInstance();
        store.save(item);
    }

    @Override
    protected Part getModelItem() {
        Part part = model.getPart();

        try{
            part.setPrice(getMoneyValue(txtPrice));
        }
        catch(ParseException ex){
            //this should not get here because validation should pick it up
            part.setPrice(0);
        }
        return part;
    }


    @Override
    protected ArrayList<String> getValiddationMessages(Part part){
        ArrayList<String> errors = new ArrayList<>();

        int inStock = part.getInStock();
        int min = part.getMin();
        int max = part.getMax();

        if (min > max){
            errors.add("Min can't be greater than max");
        }
        if (max < min){
            errors.add("Max can't be less than min");
        }

        if (inStock < min || inStock > max){
            errors.add("Inventory quantity must be between min and max values");
        }

        if (part.getName() == null || part.getName().isEmpty()){
            errors.add("Part must have a name");
        }

        try{
            getMoneyValue(txtPrice);
        }
        catch (ParseException ex){
            errors.add("Price has an invalid value");
        }
        return errors;

    }
}
