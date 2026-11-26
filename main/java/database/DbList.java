package database;

import java.io.Serializable;
import java.util.*;

public class DbList implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<DbItem> items;
    public DbList(){
        items = new ArrayList<>(List.of());
    }

    public List<DbItem> getItems() {
        return items;
    }

    public void setItems(List<DbItem> items){
        this.items = items;
    }

    public void add(DbItem item){
        items.add(item);
    }

    public DbItem search(int id){
        for(DbItem item : items){
            if(item.getId() == id){
                return item;
            }
        }
        return null;
    }
    
    public DbList search(String name){
        DbList matches = new DbList();
        for(DbItem item : items){
            if(item.getName().contains(name)){
                matches.add(item);
            }
        }
        return matches;
    }

    public void remove(DbItem item){
        items.remove(item);
    }

    public void remove(int id){
        remove(search(id));
    }

    public boolean contains(DbItem item){
        return items.contains(item);
    }

    public String toString(){
        String ret = "List of items: \n";
        for (DbItem item : items){
            ret += item.toString().indent(2);
        }
        return ret;
    }

}
