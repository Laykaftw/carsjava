package com.miniprojet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordtf;

    @FXML
    private TextField usernametf;

    @FXML
    void Login(ActionEvent event) throws IOException {
        if (chechlogin(usernametf.getText(), passwordtf.getText())) {
            System.out.println("Login successful");
            App.setRoot("maindashboard");
            
        } else {
            System.out.println("Login failed");
        }
    }
    public boolean chechlogin(String username, String password){
        File file = new File("Users.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts =line.split(" ");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    return true ;
                }
            }
    } catch (IOException e) {
        System.out.println("An error occurred while reading the input file.");
        e.printStackTrace();
    }
        return false;
    }

}
