package database;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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

    public DbList searchById(String idPrefix) {
        DbList matches = new DbList();
        matches.setItems(items.stream()
                .filter(item -> String.valueOf(item.getId()).startsWith(idPrefix))
                .collect(Collectors.toList()));
        return matches;
    }

    public DbList searchByName(String prefix){
        DbList matches = new DbList();
        matches.setItems(items.stream()
                .filter(item -> item.getName() != null && item.getName().toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList()));
        return matches;
    }
    
    public DbItem getByName(String name) {
        if (name == null) {
            return null;
        }
        return items.stream()
                .filter(item -> item.getName() != null && item.getName().equalsIgnoreCase(name.trim()))
                .findFirst()
                .orElse(null);
    }
    
    public void remove(DbItem item){
        items.remove(item);
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
