package domain.parts;

import domain.BaseEntity;

public abstract class Part extends BaseEntity {
    private int partID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

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
 
    public int getPartID(){
        return partID;
    }
    public void setPartID(int value){
        partID = value;
        setId(value);
    }
}

