package employee;

public abstract class Employee {
    private static int idCount = 0;
    private final int id;
    private String fullName;
    private int age;
    private String position;
    private Department department;

    public Employee(String fullName, int age, String position, Department department) {
        this.id = idCount++;
        this.fullName = fullName;
        this.age = age;
        this.position = position;
        this.department = department;
    }

    abstract int calculateSalary();
}
