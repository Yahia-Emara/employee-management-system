package database;

import employee.EmployeeList;

public class Database {
    private EmployeeList employees;
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
    }
    
    public void save(){
        Helper.writeFile("./database/employees.ser", employees);
    }

    public EmployeeList getEmployees(){
        return employees;
    }
}
