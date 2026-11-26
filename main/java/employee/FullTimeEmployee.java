package employee;

public class FullTimeEmployee extends Employee{
    private int monthlySalary;
    private int bonus;
    
    public FullTimeEmployee(String fullName, int age, String position, Department department, int monthlySalary, int bonus) {
        super(fullName, age, position, department);
        this.monthlySalary = monthlySalary;
        this.bonus = bonus;
    }
    
    public int getMonthlySalary() { return monthlySalary; }
    public void setMonthlySalary(int monthlySalary) { this.monthlySalary = monthlySalary; }
    public int getBonus() { return bonus; }
    public void setBonus(int bonus) { this.bonus = bonus; }
    
    @Override
    public int calculateSalary() {
        return monthlySalary + bonus;
    }

    @Override 
    public String toString() {
        return super.toString()+
               ("monthly salary: %d EGP\n".formatted(monthlySalary)+
                "bonus: %d EGP\n".formatted(bonus)+
                "total salary: %d EGP\n".formatted(calculateSalary())).indent(4);
    }
}
