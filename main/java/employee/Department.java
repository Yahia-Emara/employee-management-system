package employee;

import java.io.Serializable;
import database.DbList;

public class Department implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int idCount = 0;
    private final int id;
    private String name;
    private DbList employees;

    public Department(String name) {
        this.id = idCount++;
        this.name = name;
        this.employees = new DbList();
    }

    public Department(String name, DbList employees) {
        this.id = idCount++;
        this.name = name;
        this.employees = employees;
    }
    
    public static void setIdCount(int idCount){
        Department.idCount = idCount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DbList getEmployees() {
        return employees;
    }

    public void setEmployees(DbList employees) {
        this.employees = employees;
    }

    public String toString(){
        return "Department:\n"+
               ("ID: %d\n".formatted(id)+
                "name: %s\n".formatted(name)).indent(2);
    
    }

}
