package employee;

public class FullTimeEmployee extends Employee{
    private int monthlySalary;
    private int bonus;
    
    public FullTimeEmployee(String fullName, int age, String position, Department department, int monthlySalary, int bonus) {
        super(fullName, age, position, department);
        this.monthlySalary = monthlySalary;
        this.bonus = bonus;
    }

    @Override
    int calculateSalary() {
        return monthlySalary + bonus;
    }
}
