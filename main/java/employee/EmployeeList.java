package employee;

import java.io.Serializable;
import java.util.*;

public class EmployeeList implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Employee> employees;
    public EmployeeList(){
        employees = new ArrayList<>(List.of());
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public EmployeeList(List<Employee> employees){
        this.employees = employees;
    }

    public void add(Employee emp){
        employees.add(emp);
    }

    public Employee search(int id){
        for(Employee emp : employees){
            if(emp.getId() == id){
                return emp;
            }
        }
        return null;
    }
    
    public EmployeeList search(String name){
        EmployeeList matches = new EmployeeList();
        for(Employee emp : employees){
            if(emp.getFullName().contains(name)){
                matches.add(emp);
            }
        }
        return matches;
    }

    public void remove(Employee emp){
        employees.remove(emp);
    }

    public void remove(int id){
        remove(search(id));
    }

    public String toString(){
        String ret = "List of employees: \n";
        for (Employee emp : employees){
            ret += emp.toString().indent(2);
        }
        return ret;
    }
}
