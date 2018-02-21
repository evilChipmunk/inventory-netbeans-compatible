package domain;

import domain.parts.Part;

import java.util.ArrayList;

public class Product  extends BaseEntity{
    private ArrayList<Part> associatedParts;
    private int productID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    public Product(){
        associatedParts = new ArrayList<>();
    }

    public String getName(){
        return name;
    }
    public void setName(String value){
        name = value;
    }

    public double getPrice(){
        return price;
    }
    public void setPrice(double value){
        price = value;
    }

    public int getInStock(){
        return inStock;
    }
    public void setInStock(int value){
        inStock = value;
    }

    public int getMin(){
        return min;
    }
    public void setMin(int value){
        min = value;
    }

    public int getMax(){
        return max;
    }
    public void setMax(int value){
        max = value;
    }

    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    public boolean removeAssociatedPart(int partID){
        synchronized (this){
            Part foundPart = lookupAssociatedPart(partID);
            if (foundPart != null){
                return associatedParts.remove(foundPart);
            }
            return false;
        }
    }

    public Part lookupAssociatedPart(int partID) {
        for (Part part : associatedParts) {
            if (part.getPartID() == partID) {
                return part;
            }
        }
        return null;
    }

    public int getProductID(){
        return productID;
    }
    public void setProductId(int value){
        setId(value);
        productID = value;
    }

    public ArrayList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public boolean hasParts() {
        return getAssociatedParts().size() > 0;
    }
}
