/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.manageEmployee;

import database.Database;
import database.DbItem;
import database.DbList;
import employee.Department;
import employee.Employee;
import gui.addEmployee.addEmployee;
import gui.common;
import static gui.common.isNumeric;
import static gui.common.showAlert;
import gui.editEmployee.EditEmployee;
import gui.home.Home;
import gui.login.Login;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        
        tableView.setItems(FXCollections.observableArrayList(
                Database.get().getEmployees().getItems()
        ));
        searchEmployeesField.textProperty().addListener((obs, oldVal, newVal) -> searchEmployee());
    }
    
   
    public void searchEmployee(){
        
        Database db = Database.get();
        DbList emp;
        String input = searchEmployeesField.getText() == null ? "" : searchEmployeesField.getText().trim();
         if (input.isEmpty()) {
            emp = Database.get().getEmployees();
        }
         else if(isNumeric(input)){
        emp=db.getEmployees().searchById(input);
        }else{
        emp=db.getEmployees().searchByName(input);
        }
        if (emp == null || emp.getItems() == null) {
            tableView.getItems().clear();
        } else {
            tableView.getItems().setAll(emp.getItems());
        }
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
      if(selectedEmployee ==null){showAlert("NO SELECTED EMPLOYEE");
     }else{
     Database.get().getEmployees().remove(selectedEmployee);
      for(DbItem department : Database.get().getDepartments().getItems()){
          Department dept = (Department) department;
          Employee emp = (Employee) selectedEmployee;
          if(dept.getId() == emp.getDepartment().getId()){
          dept.removeEmployee(selectedEmployee);
          
          break;
          }
          
       }
      Database.get().save();
      tableView.getItems().remove(selectedEmployee);
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
