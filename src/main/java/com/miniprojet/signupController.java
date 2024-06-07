package com.miniprojet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class signupController {

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordtf;

    @FXML
    private Button signupButton;

    @FXML
    private TextField usernametf;

    @FXML
    void Login(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    void SignUp(ActionEvent event) throws IOException {
        String username = usernametf.getText();
        String password = passwordtf.getText();
        if (Sign(username, password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sign Up Successful");
            alert.setHeaderText(null);
            alert.setContentText("Sign up successful! You can now log in.");
            alert.showAndWait();
            App.setRoot("login");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sign Up Failed");
            alert.setHeaderText(null);
            alert.setContentText("Sign up failed. Please try again.");
            alert.showAndWait();
        }

    }

    private boolean Sign(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Users.txt", true))) {
            writer.write(username + " " + password + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
