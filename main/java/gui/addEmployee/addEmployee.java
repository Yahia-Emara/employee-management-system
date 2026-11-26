
package gui.addEmployee;
import database.Database;
import database.DbItem;
import database.DbList;
import employee.Department;
import employee.FullTimeEmployee;
import employee.PartTimeEmployee;
import gui.common;
import static gui.common.isValidName;
import gui.home.Home;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import static gui.common.showAlert;
import static gui.common.showSuccess;

public class addEmployee {

    
    
    @FXML
    private TextField fullNameField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField positionField;

    @FXML
    private TextField basicSalaryField;

    @FXML
    private TextField bonusField;

    @FXML
    private TextField hourlyRate;

    @FXML
    private TextField hoursWorked;

    @FXML
    private ComboBox<String> departmentBox;

    @FXML
    private ComboBox<String> typeBox;

    @FXML
    private GridPane fullTimeBox;

    @FXML
    private GridPane partTimeBox;


    @FXML
    public static void start(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(addEmployee.class.getResource("addEmployee.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }
    
    @FXML
    public void initialize() {

       List<String> departments = new ArrayList<>();
       for(DbItem department : Database.get().getDepartments().getItems()){
           departments.add(department.getName());
       }
        departmentBox.getItems().addAll(departments);
        typeBox.getItems().addAll(
            "Full-Time",
            "Part-Time"
        );

        fullTimeBox.setVisible(true);
        fullTimeBox.setManaged(true);

        partTimeBox.setVisible(false);
        partTimeBox.setManaged(false);
        
        typeBox.setOnAction(event -> {

        boolean isPartTime = "Part-Time".equals(typeBox.getValue());

        fullTimeBox.setVisible(!isPartTime);
        fullTimeBox.setManaged(!isPartTime);

        partTimeBox.setVisible(isPartTime);
        partTimeBox.setManaged(isPartTime);
    });
    }

    @FXML
    private void handleAddBtn(ActionEvent event) {

        try {
            
            String name = fullNameField.getText().trim();
            String position = positionField.getText().trim();
            String departmentName = departmentBox.getValue();
            String employeeType = typeBox.getValue();

            if (name.isEmpty() || ageField.getText().trim().isEmpty()
                    || position.isEmpty() || departmentName == null
                    || employeeType == null) {

                showAlert("Please fill in all required fields.");
                return;
            }
            if (!isValidName(name)) {
                showAlert("Full name must contain letters only.");
                return;
            }

            if (!isValidName(position)) {
                showAlert("Position must contain letters only.");
                return;
            }

            String ageText = ageField.getText().trim();
            if (!common.isNumeric(ageText)) {
                showAlert("Age must be a whole number.");
                return;
            }

            int age = Integer.parseInt(ageText);
            if (age <= 0) {
                showAlert("Age must be positive.");
                return;
            }
            DbItem found = Database.get().getDepartments().getByName(departmentName);
            if (found == null) {
                showAlert("Department not found.");
                return;
            }
            Department department = (Department) found;
            if ("Full-Time".equals(employeeType)) {

                int salary = Integer.parseInt(
                    basicSalaryField.getText().trim()
                );

                int bonus = Integer.parseInt(
                    bonusField.getText().trim()
                );

                if (salary < 0 || bonus < 0) {
                    showAlert("Salary and bonus cannot be negative.");
                    return;
                }

                FullTimeEmployee emp = new FullTimeEmployee(name,age,position,department,salary,bonus);
                department.getEmployees().add(emp);
                Database db = Database.get();
                db.getEmployees().add(emp);
                db.save();
                showSuccess("Employee added successfully!");
                handleClearBtn(null);
                try {
                        Home.start(event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            } else if ("Part-Time".equals(employeeType)) {

                int rate = Integer.parseInt(
                    hourlyRate.getText().trim()
                );

                int hours = Integer.parseInt(
                    hoursWorked.getText().trim()
                );

                if (rate < 0 || hours < 0) {
                    showAlert("Rate and hours cannot be negative.");
                    return;
                }

                PartTimeEmployee partemp = new PartTimeEmployee(name,age,position,department,rate,hours);
                department.getEmployees().add(partemp);
                Database db = Database.get();
                db.getEmployees().add(partemp);
                db.save();
                showSuccess("Employee added successfully!");
                handleClearBtn(null);
                try {
                      Home.start(event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }

        } catch (NumberFormatException e) {

            showAlert("Please enter valid numeric values.");

        }
        
    }

    @FXML
    private void handleClearBtn(ActionEvent event) {

        fullNameField.clear();
        ageField.clear();
        positionField.clear();

        basicSalaryField.clear();
        bonusField.clear();

        hourlyRate.clear();
        hoursWorked.clear();

        departmentBox.getSelectionModel().clearSelection();
        typeBox.getSelectionModel().clearSelection();

        fullTimeBox.setVisible(true);
        fullTimeBox.setManaged(true);

        partTimeBox.setVisible(false);
        partTimeBox.setManaged(false);
    }
    @FXML
    private void handleBackBtn(ActionEvent event) {
        try {
            Home.start(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
