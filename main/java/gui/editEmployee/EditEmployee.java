package gui.editEmployee;

import database.Database;
import database.DbItem;
import employee.Department;
import employee.Employee;
import employee.FullTimeEmployee;
import employee.PartTimeEmployee;
import gui.common;
import gui.home.Home;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

import static gui.common.showFieldsFor;
import static gui.common.validateEmployeeFields;
import static gui.common.validateFullTime;
import static gui.common.validatePartTime;
import static gui.common.showAlert;
import static gui.common.showSuccess;

public class EditEmployee {

    @FXML private TextField fullNameField;
    @FXML private TextField ageField;
    @FXML private TextField positionField;
    @FXML private TextField basicSalaryField;
    @FXML private TextField bonusField;
    @FXML private TextField hourlyRate;
    @FXML private TextField hoursWorked;
    @FXML private ComboBox<String> departmentBox;
    @FXML private ComboBox<String> typeBox;
    @FXML private GridPane fullTimeBox;
    @FXML private GridPane partTimeBox;

    private Employee employee;

    public static void start(ActionEvent event, DbItem selected) throws Exception {
        FXMLLoader loader = new FXMLLoader(EditEmployee.class.getResource("EditEmployee.fxml"));
        Parent root = loader.load();
        EditEmployee controller = loader.getController();
        controller.setEmployee(selected);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @FXML
    public void initialize() {
        for (DbItem department : Database.get().getDepartments().getItems()) {
            departmentBox.getItems().add(department.getName());
        }
        typeBox.getItems().addAll("Full-Time", "Part-Time");
        typeBox.setOnAction(e -> showFieldsFor(fullTimeBox, partTimeBox, typeBox.getValue()));
    }

    public void setEmployee(DbItem item) {
        // can remove if manage employee button always check if employee is selected
        if (!(item instanceof Employee)) {
            showAlert("Select an employee to edit.");
            return;
        }
        
        employee = (Employee) item;

        fullNameField.setText(employee.getFullName());
        ageField.setText(String.valueOf(employee.getAge()));
        positionField.setText(employee.getPosition());

        Department current = findDepartmentById(employee.getDepartment().getId());
        departmentBox.setValue(current != null ? current.getName() : employee.getDepartment().getName());

        if (employee instanceof FullTimeEmployee) {
            FullTimeEmployee full = (FullTimeEmployee) employee;
            typeBox.setValue("Full-Time");
            basicSalaryField.setText(String.valueOf(full.getMonthlySalary()));
            bonusField.setText(String.valueOf(full.getBonus()));
        } else if (employee instanceof PartTimeEmployee) {
            PartTimeEmployee part = (PartTimeEmployee) employee;
            typeBox.setValue("Part-Time");
            hourlyRate.setText(String.valueOf(part.getHourlyPaymentRate()));
            hoursWorked.setText(String.valueOf(part.getHoursWorked()));
        }
        showFieldsFor(fullTimeBox, partTimeBox, typeBox.getValue());
    }

    @FXML
    private void handleEditBtn(ActionEvent event) {
        if (employee == null) {
            showAlert("No employee selected.");
            return;
        }
        try {
            String name = fullNameField.getText().trim();
            String position = positionField.getText().trim();
            String departmentName = departmentBox.getValue();
            String type = typeBox.getValue();
            String ageText = ageField.getText().trim();

            String error = validateEmployeeFields(name, position, ageText, departmentName, type);
            if (error != null) {
                showAlert(error);
                return;
            }
            int age = Integer.parseInt(ageText);
            Department newDept = (Department) Database.get().getDepartments().getByName(departmentName);
            if (newDept == null) {
                showAlert("Department not found.");
                return;
            }

        int salary = 0, bonus = 0, rate = 0, hours = 0;
        if ("Full-Time".equals(type)) {
            error = validateFullTime(basicSalaryField.getText().trim(), bonusField.getText().trim());
            if (error != null) {
                showAlert(error);
                return;
            }
            salary = Integer.parseInt(basicSalaryField.getText().trim());
            bonus = Integer.parseInt(bonusField.getText().trim());
        } else {
            error = validatePartTime(hourlyRate.getText().trim(), hoursWorked.getText().trim());
            if (error != null) {
                showAlert(error);
                return;
            }
            rate = Integer.parseInt(hourlyRate.getText().trim());
            hours = Integer.parseInt(hoursWorked.getText().trim());
        }

            int oldId = employee.getId();
            Department oldDept = findDepartmentById(employee.getDepartment().getId());
            Employee edited;

            boolean sameType = ("Full-Time".equals(type) && employee instanceof FullTimeEmployee)
                || ("Part-Time".equals(type) && employee instanceof PartTimeEmployee);

            if (sameType) {
                edited = employee;
                edited.setFullName(name);
                edited.setAge(age);
                edited.setPosition(position);
                edited.setDepartment(newDept);
                if (edited instanceof FullTimeEmployee) {
                    ((FullTimeEmployee) edited).setMonthlySalary(salary);
                    ((FullTimeEmployee) edited).setBonus(bonus);
                } else {
                    ((PartTimeEmployee) edited).setHourlyPaymentRate(rate);
                    ((PartTimeEmployee) edited).setHoursWorked(hours);
                }
            } else {
                if ("Full-Time".equals(type)) {
                    edited = new FullTimeEmployee(name, age, position, newDept, salary, bonus);
                } else {
                    edited = new PartTimeEmployee(name, age, position, newDept, rate, hours);
                }

                Database.get().getEmployees().getItems().removeIf(i -> i.getId() == oldId);
                Database.get().getEmployees().add(edited);
            }

            if (oldDept != null) {
                oldDept.getEmployees().getItems().removeIf(i -> i.getId() == oldId);
            }
            newDept.getEmployees().getItems().removeIf(i -> i.getId() == oldId);
            newDept.getEmployees().add(edited);

            if (oldDept != null) {
                oldDept.getEmployees().getItems().removeIf(i -> i.getId() == oldId);
            }
            newDept.getEmployees().getItems().removeIf(i -> i.getId() == oldId);
            newDept.getEmployees().add(edited);

            Database.get().save();
            showSuccess("Employee updated successfully!");
            handleBackBtn(event);

        } catch (NumberFormatException e) {
            showAlert("Please enter valid numeric values.");
        }
    }

    @FXML
    private void handleBackBtn(ActionEvent event) {
        // TODO (Manage Employees): replace Home.start with the Manage Employees
        // screen's start method once it exists.
        try {
            Home.start(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private Department findDepartmentById(int id) {
        for (DbItem item : Database.get().getDepartments().getItems()) {
            if (item.getId() == id) {
                return (Department) item;
            }
        }
        return null;
    }
}