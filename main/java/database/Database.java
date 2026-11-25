package database;

import employee.Employee;
import employee.EmployeeList;
import user.User;
import user.UserList;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private EmployeeList employees;
    private UserList users;
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
        employees = (EmployeeList) Helper.readFile("./database/employees.ser");
        if (employees == null){
            employees = new EmployeeList();
        }
        else{
            Employee.setIdCount(employees.getEmployees().size());
        }
        users = (UserList) Helper.readFile("./database/users.ser");
        if (users == null){
            users = new UserList();
        }

    }
    
    public void save(){
        Helper.writeFile("./database/employees.ser", employees);
        Helper.writeFile("./database/users.ser", users);
    }

    public EmployeeList getEmployees(){
        return employees;
    }
    public UserList getUsers(){
        return users;
    }
}
