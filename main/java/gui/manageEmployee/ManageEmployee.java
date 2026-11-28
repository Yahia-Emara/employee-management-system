/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.manageEmployee;

import database.Database;
import database.DbItem;
import employee.Employee;
import gui.addEmployee.addEmployee;
import static gui.common.showAlert;
import gui.editEmployee.EditEmployee;
import gui.home.Home;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ManageEmployee {
    @FXML 
    private TextField searchEmployeesField;
    
     @FXML
    private TableView<DbItem> tableView;

    @FXML
    private TableColumn<DbItem, Integer> idColumn;

    @FXML
    private TableColumn<DbItem, String> nameColumn;

    @FXML
    private TableColumn<DbItem, Integer> ageColumn;
    
    @FXML
    private TableColumn<DbItem, String> positionColumn;
    
    @FXML
    private TableColumn<DbItem, String> departmentColumn;
    
    @FXML
    private TableColumn<DbItem, String> typeColumn;
    
    @FXML
    private TableColumn<DbItem, Integer> salaryColumn;
    
    @FXML
    public static void start(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(ManageEmployee.class.getResource("manageEmployee.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }
    
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        tableView.setPlaceholder(new Label("No matching employees found."));
        tableView.setItems(FXCollections.observableArrayList(
                Database.get().getEmployees().getItems()
        ));
        searchEmployeesField.textProperty().addListener((obs, oldVal, newVal) -> searchEmployee());
    }
    
   
    public void searchEmployee(){
        String input = searchEmployeesField.getText() == null ? "" : searchEmployeesField.getText().trim();
        String lower = input.toLowerCase();
        List<DbItem> result = new ArrayList<>();
        for (DbItem item : Database.get().getEmployees().getItems()) {
            Employee e = (Employee) item;
            if (input.isEmpty()
                    || String.valueOf(e.getId()).startsWith(input)
                    || e.getFullName().toLowerCase().contains(lower)
                    || e.getDepartmentName().toLowerCase().contains(lower)) {
                result.add(item);
            }
        }
        tableView.getItems().setAll(result);
    }
    
    public void back(ActionEvent event){
    try {
            Home.start(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void add(ActionEvent event){
    try {
            addEmployee.start(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void delete(ActionEvent event){
        DbItem selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            showAlert("NO SELECTED EMPLOYEE");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete " + selectedEmployee.getName() + "?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        if (confirm.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
            return;
        }
        Database.get().getEmployees().remove(selectedEmployee);
        tableView.getItems().remove(selectedEmployee);
        if (!Database.get().save()) {
            showAlert("Could not save data to file.");
        }
    }
    
    public void edit(ActionEvent event){
     DbItem selectedEmployee = tableView.getSelectionModel().getSelectedItem();
     if(selectedEmployee ==null){showAlert("NO SELECTED EMPLOYEE");
     }else{
     try{
     EditEmployee.start(event, selectedEmployee);
     } catch(Exception e){
         e.printStackTrace();
     }
     }
    }
}
