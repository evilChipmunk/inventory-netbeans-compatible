package domain;

public abstract class BaseEntity {

    int id;

    public int getId(){
        return id;
    }

    protected void setId(int value){
        id = value;
    }

}
