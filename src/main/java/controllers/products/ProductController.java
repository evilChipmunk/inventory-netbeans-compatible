package controllers.products;

import common.*;
import controllers.AddEditController;
import controls.PartTableControl;
import domain.Product;
import domain.parts.Part;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;
import viewmodels.ProductViewModel;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

public class ProductController extends AddEditController<ProductViewModel, Product> {

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
    private Button btnAdd;
    @FXML
    private Button btnDelete;

    @FXML
    PartTableControl tblParts;

    @FXML
    PartTableControl tblProductParts;

    public ProductController(Product product) {

        model = new ProductViewModel(product);
    }

    public ProductController() {
        model = new ProductViewModel();
    }

    @FXML
    public void initialize() {

        lblId.setText(model.getId().get());
        lblTitle.setText(model.getTitle().get());

        txtName.textProperty().bindBidirectional(model.getName());
        txtInStock.textProperty().bindBidirectional(model.getInStock(), new NumberStringConverter());
//        txtPrice.textProperty().bindBidirectional(model.getPrice()
//                , new NumberStringConverter(new DecimalFormat(DecimalFormatSymbols.getInstance().getCurrency().getSymbol())));
        txtMin.textProperty().bindBidirectional(model.getMin(), new NumberStringConverter(new DecimalFormat("#")));
        txtMax.textProperty().bindBidirectional(model.getMax(), new NumberStringConverter(new DecimalFormat("#")));


        setNumberFormat(txtInStock);
        setNumberFormat(txtMin);
        setNumberFormat(txtMax);


        loadParts();

        setCurrencyTextBox(txtPrice, model.getProduct().getPrice());
        setMoneyValidation(txtPrice);

        setPartsButtonsDisabled(true);
        setSelectedPartsButtonsDisabled(true);
        setSelectionListener();

    }

    @Override
    protected void saveAction(Product item) {

        ProductDataStore store = ProductDataStore.getInstance();
        store.save(item);
    }

    @Override
    protected Product getModelItem() {
        Product product = model.getProduct();

        try{
            double price = getMoneyValue(txtPrice);
            product.setPrice(price);
        }
        catch (ParseException ex){
            product.setPrice(0);
        }

        return product;
    }

    @Override
    protected ArrayList<String> getValiddationMessages(Product product) {
        ArrayList<String> errors = new ArrayList();


        try{
            double price = getMoneyValue(txtPrice);
            product.setPrice(price);
        }
        catch (ParseException ex){
            errors.add("Price has an invalid value");
        }

        int inStock = product.getInStock();
        int min = product.getMin();
        int max = product.getMax();

        if (min > max){
            errors.add("Min can't be greater than max");
        }
        if (max < min){
            errors.add("Max can't be less than min");
        }

        if (inStock < min || inStock > max){
            errors.add("Inventory quantity must be between min and max values");
        }

        if (!product.hasParts()){
            errors.add("Product must have at least one part");
        }

        double partSum = 0;
        for (Part part: product.getAssociatedParts()) {
            partSum += part.getPrice();
        }

        if (partSum > product.getPrice()){
            errors.add("The product price cannot be less than the price of all the parts");
        }

        if (product.getName() == null || product.getName().isEmpty()){
            errors.add("Product must have a name");
        }
        if (product.getPrice() == 0){
            errors.add("Product must have a price greater than zero");
        }
        if (product.getInStock() < 0){
            errors.add("Product must have an inventory level greater than zero");
        }
        return errors;
    }

    @FXML
    public void addPart(ActionEvent event){

        Part selectedPart = tblParts.getSelectedPart();

        Product product = model.getProduct();
        product.addAssociatedPart(selectedPart);
        loadParts();
    }

    @FXML
    public void deletePart(ActionEvent event){
        Product product = model.getProduct();
        product.removeAssociatedPart(tblProductParts.getSelectedPart().getPartID());
        loadParts();
    }


    private void loadParts(){

        tblParts.setTableDataSource(new ObservableCollection<>());
        tblProductParts.setTableDataSource(new ObservableCollection<>());

        Product product = model.getProduct();

        PartDataStore store = PartDataStore.getInstance();
        ObservableCollection<Part> parts = store.getParts();

        ObservableCollection<Part> unselectedParts = new ObservableCollection<>();
        ObservableCollection<Part> selectedParts = new ObservableCollection<>();


        //kind of a weird / inefficient way to do this, product parts would normally just
        //be loaded from a database, but coded to use methods per requirements
        for (Part part: parts ) {
            Part foundPart = product.lookupAssociatedPart(part.getPartID());
            if (foundPart == null){
                unselectedParts.add(part);
            }
            else{
                selectedParts.add(foundPart);
            }
        }

        tblParts.setTableDataSource(unselectedParts);
        tblProductParts.setTableDataSource(selectedParts);
    }


    protected void setPartsButtonsDisabled(boolean disabled) {

        btnAdd.setDisable(disabled);
    }


    protected void setSelectedPartsButtonsDisabled(boolean disabled) {

        btnDelete.setDisable(disabled);
    }

    private void setSelectionListener(){
        ListChangeListener< Part> partsSelection = new ListChangeListener<Part>(){
            @Override
            public void onChanged(  ListChangeListener.Change<? extends Part> changed){
                setPartsButtonsDisabled(changed.getList().size() <= 0);
            }
        };
        ListChangeListener< Part> productPartsSelection = new ListChangeListener<Part>(){
            @Override
            public void onChanged(  ListChangeListener.Change<? extends Part> changed){
                setSelectedPartsButtonsDisabled(changed.getList().size() <= 0);
            }
        };
        tblParts.getTblParts().getSelectionModel().getSelectedItems().addListener(partsSelection);
        tblProductParts.getTblParts().getSelectionModel().getSelectedItems().addListener(productPartsSelection);

    }
}
