package common;

import domain.Product;

//Data storage for products
public class ProductDataStore {

    private static ProductDataStore instance = null;

    private ObservableCollection<Product> products;

    private ProductDataStore() {
        // Exists only to defeat instantiation.
        products = new ObservableCollection<Product>();
    }

    public static ProductDataStore getInstance() {
        if(instance == null) {
            instance = new ProductDataStore();
        }
        return instance;
    }

    public ObservableCollection<Product> getProducts() {
        return products;
    }

    //gets the next available product id. Ids are reusable if a product is deleted
    public int getNextProductId(){

        int size = products.getList().size();
        if (size == 0){
            return 1;
        }

        int id =  products.getList().get(size -1)  .getProductID();
        id++;
        return id;
    }

    public Product findProduct(int productId){
        return products.find(productId);
    }

    //inserts or updates product
    public void save(Product product){
        Product foundProduct = findProduct(product.getProductID());

        if (foundProduct == null){
            getProducts().getList().add(product);
        }
        else{
            getProducts().modify(product);
        }
    }

    public void removeProduct(Product product) {
        getProducts().remove(product);
    }
}
