package viewmodels;

import common.ProductDataStore;
import domain.Product;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProductViewModel extends BaseViewModel {

    private SimpleStringProperty id;
    private SimpleStringProperty title;
    private SimpleBooleanProperty isInHousePart;
    private SimpleStringProperty name;
    private SimpleIntegerProperty inStock;
    private SimpleDoubleProperty price;
    private SimpleIntegerProperty min;
    private SimpleIntegerProperty max;
    private SimpleIntegerProperty machineId;
    private SimpleStringProperty companyName;

    private final String defaultIdText = "Auto-Gen Disabled";
    private final String addTitle = "Add Product";
    private final String modifyTitle = "Modify Product";
    private final int defaultId = 0;

    private Product productState;

    public ProductViewModel(Product product) {
        productState = product;
        setProperties(productState);
    }

    public ProductViewModel() {

        //field is set in this view model because state is needed for adding/removing parts
        productState = new Product();

         setProperties(productState);
    }

    public Product getProduct() {
        //if product is brand new, get the next available id
        if (id.get() == defaultIdText){
            productState.setProductId(ProductDataStore.getInstance().getNextProductId());
        }
        else{

            productState.setProductId( Integer.parseInt(id.get()));
        }

        productState.setName(name.get());
        productState.setInStock(inStock.get());
        productState.setPrice(price.get());
        productState.setMin(min.get());
        productState.setMax(max.get());

        return productState;
    }

    private void setProperties(Product model){

        if (model.getProductID() == defaultId){
            id = new SimpleStringProperty(defaultIdText);
            title = new SimpleStringProperty(addTitle);
        }
        else {
            id = new SimpleStringProperty(((Integer) model.getProductID()).toString());
            title = new SimpleStringProperty(modifyTitle);
        }
        name = new SimpleStringProperty(model.getName());
        inStock = new SimpleIntegerProperty(model.getInStock());
        price = new SimpleDoubleProperty(model.getPrice());
        min = new SimpleIntegerProperty(model.getMin());
        max = new SimpleIntegerProperty(model.getMax());

    }


    public SimpleStringProperty getId() {
        return id;
    }

    public SimpleStringProperty getTitle() {
        return title;
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public SimpleIntegerProperty getInStock() {
        return inStock;
    }

    public SimpleDoubleProperty getPrice() {
        return price;
    }

    public SimpleIntegerProperty getMin() {
        return min;
    }

    public SimpleIntegerProperty getMax() {
        return max;
    }

}
