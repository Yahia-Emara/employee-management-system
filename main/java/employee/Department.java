package employee;

public class Department {
    private static int idCount = 0;
    private final int id;
    private String name;
    private EmployeeList employees;

    public Department(String name) {
        this.id = idCount++;
        this.name = name;
        this.employees = new EmployeeList();
    }

    public Department(String name, EmployeeList employees) {
        this.id = idCount++;
        this.name = name;
        this.employees = employees;
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

    public EmployeeList getEmployees() {
        return employees;
    }

    public void setEmployees(EmployeeList employees) {
        this.employees = employees;
    }

    public String toString(){
        return "Department:\n"+
               ("ID: %d\n".formatted(id)+
                "name: %s\n".formatted(name)).indent(2);
    
    }

}
