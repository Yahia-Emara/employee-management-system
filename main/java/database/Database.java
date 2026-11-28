package database;

import employee.Department;
import employee.Employee;
import user.User;

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
            Employee.setIdCount(nextId(employees));
        }

        users = (DbList) Helper.readFile("./database/users.ser");
        if (users == null){
            users = new DbList();
        }
        if (users.getItems().isEmpty()){
            users.add(new User("Admin", "admin123"));
            Helper.writeFile("./database/users.ser", users);
        }
        departments = (DbList) Helper.readFile("./database/departments.ser");
        if (departments == null){
            departments = new DbList();
            String[] defaultDepartments = {
                "IT",
                "Human Resources",
                "Finance",
                "Marketing",
                "Sales"
            };
            for (String departmentName : defaultDepartments) {
                departments.add(new Department(departmentName));
            }
            Helper.writeFile("./database/departments.ser", departments);
        }
        else{
            Department.setIdCount(nextId(departments));
        }

    }
    
    public boolean save(){
        boolean ok = Helper.writeFile("./database/employees.ser", employees);
        ok &= Helper.writeFile("./database/users.ser", users);
        ok &= Helper.writeFile("./database/departments.ser", departments);
        return ok;
    }
    
    private int nextId(DbList list) {
        int max = -1;
        for (DbItem item : list.getItems()) {
            if (item.getId() > max) {
                max = item.getId();
            }
        }
        return max + 1;
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
