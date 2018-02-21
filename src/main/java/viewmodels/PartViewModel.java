package viewmodels;

import common.PartDataStore;
import domain.parts.InHousePart;
import domain.parts.OutsourcedPart;
import domain.parts.Part;
import javafx.beans.property.*;

public class PartViewModel extends BaseViewModel {

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
    private final String addTitle = "Add Part";
    private final String modifyTitle = "Modify Part";
    private final int defaultId = 0;

    public PartViewModel()  {

//        InHousePart part = new InHousePart();
//
//        part.setName("Fred");
//        part.setInStock(25);
//        part.setPrice(5.00);
//        part.setMin(10);
//        part.setMax(2000);
//        part.setMachineId(1234);
//
//
//        setProperties(part);
       setProperties(new InHousePart());
    }

    public PartViewModel(Part partModel) {
        Integer id = partModel.getPartID();
        setProperties(partModel);
    }

    public Part getPart() {

        Part part;
        if (getIsInHouse().get()){
            part = new InHousePart();
            ((InHousePart)part).setMachineId(machineId.get());
        }
        else{
            part = new OutsourcedPart();
            ((OutsourcedPart)part).setCompanyName(companyName.get());
        }
        //if part is brand new, get the next available id
        if (id.get() == defaultIdText){
            part.setPartID(PartDataStore.getInstance().getNextPartId());
        }
        else{

            part.setPartID( Integer.parseInt(id.get()));
        }

        part.setName(name.get());
        part.setInStock(inStock.get());
        part.setPrice(price.get());
        part.setMin(min.get());
        part.setMax(max.get());

        return part;
    }

    private void setProperties(Part partModel){

        if (partModel.getPartID() == defaultId){
            id = new SimpleStringProperty(defaultIdText);
            title = new SimpleStringProperty(addTitle);
        }
        else {
            id = new SimpleStringProperty(((Integer) partModel.getPartID()).toString());
            title = new SimpleStringProperty(modifyTitle);
        }
        name = new SimpleStringProperty(partModel.getName());
        inStock = new SimpleIntegerProperty(partModel.getInStock());
        price = new SimpleDoubleProperty(partModel.getPrice());
        min = new SimpleIntegerProperty(partModel.getMin());
        max = new SimpleIntegerProperty(partModel.getMax());


        boolean isInHouse = partModel instanceof InHousePart;
        isInHousePart = new SimpleBooleanProperty(isInHouse);


        if (getIsInHouse().get()) {
            InHousePart inHouse = (InHousePart) partModel;
            machineId = new SimpleIntegerProperty(inHouse.getMachineId());
            companyName = new SimpleStringProperty("");
        } else {
            OutsourcedPart outSourced = (OutsourcedPart) partModel;
            machineId = new SimpleIntegerProperty(0);
            companyName = new SimpleStringProperty(outSourced.getCompanyName());
        }
    }

    public SimpleBooleanProperty getIsInHouse() {
        return isInHousePart;
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

    public SimpleIntegerProperty getMachineId() {
        return machineId;
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

    public SimpleStringProperty getCompanyName() {
        return companyName;
    }

}
