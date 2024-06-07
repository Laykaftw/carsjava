package com.miniprojet;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class updatecarController {

    @FXML
    private TextField makertf;

    @FXML
    private TextField modeltf;

    @FXML
    private TextField yeartf;

    @FXML
    private TextField pricetf;

    public void initialize(Car car){
        System.out.println("Initializing with car: " + car);  // Debugging line
        makertf.setPromptText(car.getMaker());
        // makertf.setText(car.getMaker());
        modeltf.setText(car.getModel());
        yeartf.setText(String.valueOf(car.getYear()));
        pricetf.setText(String.valueOf(car.getPrice()));
    }




    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    void updateCar(ActionEvent event) {
        String query = "UPDATE Car SET maker=?, model=?, year=?, price=? WHERE id=?";
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
            PreparedStatement preparedStatement = Connexion.getConnexion().prepareStatement(query);
            preparedStatement.setString(1, maker);
            preparedStatement.setString(2, model);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, year);
            // preparedStatement.setInt(5, car.getId());
            preparedStatement.executeUpdate();
            System.out.println("Car updated successfully in the database:");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }

}
