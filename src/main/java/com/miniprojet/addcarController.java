package com.miniprojet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class addcarController {

    @FXML
    private Button addButton;

    @FXML
    private TextField makertf;

    @FXML
    private TextField modeltf;

    @FXML
    private TextField pricetf;

    @FXML
    private TextField yeartf;

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    void addcar(ActionEvent event) throws IOException {
        String maker = makertf.getText();
        String model = modeltf.getText();
        try {
            int year = Integer.parseInt(pricetf.getText());
            double price = Double.parseDouble(yeartf.getText());
            if (year <= 0 || price <= 0) {
                    // Show an error message if year or price is not positive
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid Input", "Year and price must be positive values.");
                    return;
            }
            String query = "Insert into car (maker,model,price,year) values (?,?,?,?)";
            PreparedStatement preparedStatement = Connexion.getConnexion().prepareStatement(query);
            preparedStatement.setString(1, maker);
            preparedStatement.setString(2, model);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, year);
            preparedStatement.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Car added successfully", "The car has been added successfully.");
            App.setRoot("maindashboard");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
