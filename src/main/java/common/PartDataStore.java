package common;

import domain.parts.Part;

//Data store for parts, in a real app this might point to a web service or database
public class PartDataStore {

    private static PartDataStore instance = null;

    private ObservableCollection<Part> parts;

    private PartDataStore() {
        // Exists only to defeat instantiation.
        parts = new ObservableCollection<Part>();
    }

    public static PartDataStore getInstance() {
        if(instance == null) {
            instance = new PartDataStore();
        }
        return instance;
    }

    public ObservableCollection<Part>  getParts(){
        return parts;
    }

    //grabs the next available part id - part ids can be reused if one is deleted
    public int getNextPartId(){

        int size = parts.getList().size();
        if (size == 0){
            return 1;
        }

        int id =  parts.getList().get(size -1).getPartID();
        id++;
        return id;
    }

    public Part findPart(int partId){
        return parts.find(partId);
    }

    //provides an upsert type of save
    public void save(Part part){
        Part foundPart = findPart(part.getPartID());

        if (foundPart == null){
            getParts().getList().add(part);
        }
        else{
            getParts().modify(part);
        }
    }

    public void removePart(Part part) {
        getParts().remove(part);
    }

}
