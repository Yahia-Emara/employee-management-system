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
    public int calculateSalary() {
        return hourlyPaymentRate + hoursWorked;
    }

    @Override 
    public String toString() {
        return super.toString()+
               ("payment rate: %d EGP/hr\n".formatted(hourlyPaymentRate)+
                "time worked: %d hrs\n".formatted(hoursWorked)+
                "total salary: %d EGP\n".formatted(calculateSalary())).indent(4);
    }
}
