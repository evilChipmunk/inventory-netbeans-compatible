package controls;

import common.*;
import controllers.BaseController;
import controllers.products.ProductController;
import domain.Product;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import viewmodels.ProductViewModel;

public class ListProductControl   extends BaseListControl<Product, ProductViewModel> {

    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnModify;
    @FXML
    private TableView tblProducts;

    @Override
    protected String getFxmlPath() {
        return Configuration.Paths.get_ListProductPath();
    }

    @Override
    protected String getAddPath() {
        return Configuration.Paths.get_AddProductPath();
    }

    @Override
    protected String getModifyPath() {
        return Configuration.Paths.get_ModifyProductPath();
    }

    @Override
    protected BaseController<ProductViewModel> getBaseController() {
        return  new ProductController();
    }

    @Override
    protected BaseController<ProductViewModel> getBaseController(Product model) {
        return new ProductController(model);
    }

    @Override
    protected boolean canDelete(){
        Product product = getSelectedItem();
        if (product.hasParts()){
            return false;
        }
        return true;
    }

    @Override
    protected String getDeleteFailureReason(){
        return super.getDeleteFailureReason() + ", the product has active parts";
    }

    @Override
    protected void deleteAction(Product item) {
        ProductDataStore store = ProductDataStore.getInstance();
        store.removeProduct(item);
    }

    @Override
    protected ObservableCollection<Product> getListFromStore() {
        ProductDataStore store = ProductDataStore.getInstance();
        return store.getProducts();
    }

    @Override
    protected Product findItemById(int id) {
        ProductDataStore store = ProductDataStore.getInstance();
        return store.findProduct(id);
    }

    @Override
    protected TableView getTable() {
        return tblProducts;
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
