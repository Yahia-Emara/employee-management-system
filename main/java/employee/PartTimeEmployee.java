package employee;

public class PartTimeEmployee extends Employee{
    private int hourlyPaymentRate;
    private int hoursWorked;
    
    public PartTimeEmployee(String fullName, int age, String position, Department department, int hourlyPaymentRate, int hoursWorked) {
        super(fullName, age, position, department);
        this.hourlyPaymentRate = hourlyPaymentRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    int calculateSalary() {
        return hourlyPaymentRate + hoursWorked;
    }
}
