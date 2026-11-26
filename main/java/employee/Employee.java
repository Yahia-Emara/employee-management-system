package employee;

import java.io.Serializable;
import database.DbItem;

public abstract class Employee implements Serializable, DbItem {
    private static final long serialVersionUID = 1L;
    private static int idCount = 0;
    private final int id;
    private String fullName;
    private int age;
    private String position;
    private Department department;

    public Employee(String fullName, int age, String position, Department department) {
        this.id = idCount++;
        this.fullName = fullName;
        this.age = age;
        this.position = position;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return getFullName();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public static void setIdCount(int idCount) {
        Employee.idCount = idCount;
    }

    abstract int calculateSalary();
    
    public String toString(){
        return "Employee:\n"+
               ("ID: %d\n".formatted(id)+
                "name: %s\n".formatted(fullName)+
                "age: %d\n".formatted(age)+
                "position: %s\n".formatted(position)+
                department.toString()).indent(4);
    }
}
