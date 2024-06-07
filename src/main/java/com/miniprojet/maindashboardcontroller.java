package com.miniprojet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedWriter;
import java.io.FileWriter;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class maindashboardcontroller {

    @FXML
    private Button addcarButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button exportButton;

    @FXML
    private ComboBox<String> makerCB;

    @FXML
    private TableColumn<Car, String> makerCol;

    @FXML
    private TableColumn<Car, String> modelCol;

    @FXML
    private TableColumn<Car, Double> priceCol;

    @FXML
    private TableColumn<Car, Integer> yearCol;

    @FXML
    private TableView<Car> tableC;

    @FXML
    private Button viewButton;

    @FXML
    void addCar(ActionEvent event) throws IOException {
        App.setRoot("Addcar");
    }

    @FXML
    void deleteCar(ActionEvent event) {
        Car selectedCar = tableC.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Car");
            alert.setHeaderText("Are you sure you want to delete this car?");
            alert.setContentText(selectedCar.toString());
            alert.showAndWait();
            if (alert.getResult().getText().equals("OK")) {
                try {
                    String query = "Delete car from car where id=?";
                    PreparedStatement preparedStatement = Connexion.getConnexion().prepareStatement(query);
                    preparedStatement.setInt(1, selectedCar.getId());
                    preparedStatement.executeUpdate();
                    tableC.getItems().remove(selectedCar);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    void editCar(ActionEvent event) throws IOException {
        Car selectedCar = tableC.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(App.class.getResource("editcar.fxml"));
            Parent root = loader.load();
            // Get the controller and call the initialize method
            updatecarController controller = loader.getController();
            controller.initialize(selectedCar);

            // Set the new scene
            App.setRoot("editCar");
        }
    }

    @FXML
    void export(ActionEvent event) {
        if (makerCB != null) {
            String maker = makerCB.getValue();
            List<Car> cars = getCarsByMaker(maker);
            if (cars != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(maker + ".txt"))) {
                    for (Car car : cars) {
                        writer.write(car.toString() + "\n");
                    }
                    showAlert(Alert.AlertType.INFORMATION, "Export Successful", "Export Successful",
                            "The cars have been exported successfully.");
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Export Failed", "Export Failed",
                            "An error occurred while exporting the cars.");
                }
            }
        }
    }

    @FXML
    void getCarList(ActionEvent event) {
        String query = "SELECT * FROM Car WHERE maker = ?";
        String maker = makerCB.getValue();
        try {
            PreparedStatement preparedStatement = Connexion.getConnexion().prepareStatement(query);
            preparedStatement.setString(1, maker);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                Car car = new Car(resultSet.getString("maker"), resultSet.getString("model"), resultSet.getInt("year"),
                        resultSet.getDouble("price"));
                cars.add(car);
            }
            tableC.setItems(FXCollections.observableArrayList(cars));
            deleteButton.setDisable(false);
            viewButton.setDisable(false);
            editButton.setDisable(false);
        } catch (SQLException ex) {
            ex.printStackTrace();
            tableC.getItems().clear();
            // return null;
        }
    }

    @FXML
    void viewCar(ActionEvent event) {

    }

    public List<Car> getCarsByMaker(String maker) {
        String query = "SELECT * FROM Car WHERE maker = ?";
        try {
            PreparedStatement preparedStatement = Connexion.getConnexion().prepareStatement(query);
            preparedStatement.setString(1, maker);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                Car car = new Car(resultSet.getString("maker"), resultSet.getString("model"), resultSet.getInt("year"),
                        resultSet.getDouble("price"));
                cars.add(car);
            }
            return cars;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void initialize() {
        makerCB.setItems(FXCollections.observableArrayList(getMakers()));
        makerCol.setCellValueFactory(new PropertyValueFactory<>("Maker"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("Model"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("Year"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

    }

    public List<String> getMakers() {
        String query = "SELECT DISTINCT maker FROM Car";
        try {
            PreparedStatement preparedStatement = Connexion.getConnexion().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> makers = new ArrayList<>();
            while (resultSet.next()) {
                makers.add(resultSet.getString("maker"));
            }
            return makers;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
