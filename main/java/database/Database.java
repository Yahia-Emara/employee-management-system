package database;

import employee.Department;
import employee.Employee;

public class Database {
    private DbList employees;
    private DbList users;
    private DbList departments;
    
    private Database(){
        load();
    }

    private static class Holder {
        private static final Database INSTANCE = new Database();
    }

    public static Database get() {
        return Holder.INSTANCE;
    }

    public void load(){
        employees = (DbList) Helper.readFile("./database/employees.ser");
        if (employees == null){
            employees = new DbList();
        }
        else{
            Employee.setIdCount(employees.getItems().size());
        }

        users = (DbList) Helper.readFile("./database/users.ser");
        if (users == null){
            users = new DbList();
        }

        departments = (DbList) Helper.readFile("./database/departments.ser");
        if (departments == null){
            departments = new DbList();
        }
        else{
            Department.setIdCount(departments.getItems().size());
        }

    }
    
    public void save(){
        Helper.writeFile("./database/employees.ser", employees);
        Helper.writeFile("./database/users.ser", users);
        Helper.writeFile("./database/departments.ser", departments);
    }

    public DbList getEmployees(){
        return employees;
    }
    public DbList getUsers(){
        return users;
    }
    public DbList getDepartments(){
        return departments;
    }
}
