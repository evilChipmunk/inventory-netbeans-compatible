package domain;

import common.ObservableCollection;
import domain.parts.Part;

public class Inventory  extends BaseEntity{
    private ObservableCollection<Product> products;
    private ObservableCollection<Part> allParts;

    public Inventory(){
        products = new ObservableCollection<Product>();
        allParts = new ObservableCollection<Part>();
    }

    public void addProduct(Product product){
        products.add(product);
    }
    public boolean removeProduct(int productId){
        return  products.remove(productId);
    }

    public Product lookupProduct(int productID){
        return products.find(productID);
    }

    public void UpdateProduct(int partID){

    }

    public void addPart(Part part){
        allParts.add(part);
    }

    public boolean deletePart(Part part){
        return  true;
    }

    public Part lookupPart(int partID){

        return allParts.find(partID);

      //  items.forEach((k,v)->System.out.println("Item : " + k + " Count : " + v));

      //  allParts.forEach(item -> {if (item.getPartID()== partID) return item;  });
//
//        for (Part part : allParts){
//            if (part.getPartID() == partID){
//                return part;
//            }
//        };
//
//        return null;

        //for (Map.Entry<String, Integer> entry : items.entrySet()) {
         //   System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
      //  }

    }

    public void updatePart(int partID){

    }
}
