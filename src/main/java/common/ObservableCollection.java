package common;

import domain.BaseEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;

//custom collection class that wraps up some of the CRUD functionality
public class ObservableCollection<T extends BaseEntity> implements Iterable<T> {

    private ObservableList<T> list;

    public ObservableCollection(){
      list = FXCollections.observableArrayList();
    }

    public Iterator<T> iterator() {
        return list.iterator();
    }

    public T find(int id){
        T entity = list.stream().filter(item -> item.getId() == id ).findAny().orElse(null);
        return entity;
    }

    public boolean add(T entity){
        return list.add(entity);
    }

    public boolean modify(T entity){
        try{
            remove(entity.getId());
            add(entity);
            return true;

        }
        catch(Exception ex){
            return false;
        }
    }

    public boolean remove(T entity){
        return list.remove(entity);
    }

    public boolean remove(int id){
        synchronized (this){
            T entity = find(id);
            if (entity != null){
                return remove(entity);
            }
            return false;
        }
    }

    public ObservableList<T> getList(){
        return list;
    }
}
