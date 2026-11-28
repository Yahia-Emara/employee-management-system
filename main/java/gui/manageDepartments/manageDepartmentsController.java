package gui.manageDepartments;

import database.Database;
import database.DbItem;
import database.DbList;
import employee.Department;
import gui.common;
import static gui.common.showAlert;
import gui.home.Home;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Optional;

public class manageDepartmentsController {

    @FXML
    private TextField departmentNameField;

    @FXML
    private TableView<DbItem> tableView;

    @FXML
    private TableColumn<DbItem, Integer> idColumn;

    @FXML
    private TableColumn<DbItem, String> nameColumn;

    @FXML
    private TableColumn<DbItem, Integer> numberOfEmployeesColumn;

    @FXML
    public static void start(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(manageDepartmentsController.class.getResource("manageDepartments.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }


    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberOfEmployeesColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfEmployees"));
        tableView.setPlaceholder(new Label("No matching departments found."));
        tableView.setItems(FXCollections.observableArrayList(
                Database.get().getDepartments().getItems()
        ));
        departmentNameField.textProperty().addListener((obs, oldVal, newVal) -> searchDepartment());
    }

    @FXML
    public void searchDepartment() {
        String input = departmentNameField.getText() == null ? "" : departmentNameField.getText().trim();
        DbList departments;

        if (input.isEmpty()) {
            departments = Database.get().getDepartments();
        } else if (common.isNumeric(input)) {
            departments = Database.get().getDepartments().searchById(input);
        } else {
            departments = Database.get().getDepartments().searchByName(input);
        }

        if (departments == null || departments.getItems() == null) {
            tableView.getItems().clear();
        } else {
            tableView.getItems().setAll(departments.getItems());
        }
    }
    @FXML
    public void back(ActionEvent event){
        try {
            Home.start(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void delete(ActionEvent event) {
        DbItem selectedDepartment = tableView.getSelectionModel().getSelectedItem();
        if (selectedDepartment != null) {
            if(selectedDepartment instanceof Department){
                Department department = (Department) selectedDepartment;
                if(department.getNumberOfEmployees()>0){
                    common.showAlert("Cannot delete department,Department has employees");
                    return;
                }
            }
            Database.get().getDepartments().remove(selectedDepartment);
            if (!Database.get().save()) {
                showAlert("Could not save data to file.");
                return;
            }
            tableView.getItems().remove(selectedDepartment);
        }
        else{
            common.showAlert("Select a Department to Delete");
        }
    }

    @FXML
    public void addDepartment(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Department");
        dialog.setHeaderText("Create a New Department");
        dialog.setContentText("Department Name:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            String trimmedName = name.trim();
            if (!trimmedName.isEmpty()) {
                if (Database.get().getDepartments().getByName(trimmedName) != null) {
                    common.showAlert("A department with this name already exists.");
                    return;
                }
                Department department = new Department(trimmedName);
                Database.get().getDepartments().add(department);
                if (!Database.get().save()) {
                    showAlert("Could not save data to file.");
                    return;
                }
                tableView.getItems().add(department);
            }
            else{
                common.showAlert("Enter a valid Department Name");
            }
        });
    }

    @FXML
    public void editDepartment(ActionEvent event) {
        DbItem selectedDepartment = tableView.getSelectionModel().getSelectedItem();
        if (selectedDepartment != null) {
                TextInputDialog dialog = new TextInputDialog(selectedDepartment.getName());
                dialog.setTitle("Edit Department");
                dialog.setHeaderText("Edit a Department");
                dialog.setContentText("Department Name:");
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(name -> {
                    String trimmedName = name.trim();
                    if (!trimmedName.isEmpty()) {
                        DbItem existing = Database.get().getDepartments().getByName(trimmedName);
                        if (existing != null && existing.getId() != selectedDepartment.getId()) {
                            common.showAlert("A department with this name already exists.");
                            return;
                        }
                        Department department = (Department) selectedDepartment;
                        department.setName(trimmedName);
                        Database.get().getDepartments().remove(selectedDepartment);
                        Database.get().getDepartments().add(department);
                        if (!Database.get().save()) {
                            showAlert("Could not save data to file.");
                            return;
                        }
                        tableView.refresh();
                    }
                    else{
                        common.showAlert("Enter a valid Department Name");
                    }
                });
        }
        else{
            common.showAlert("Select a Department to edit");
        }

    }

}
