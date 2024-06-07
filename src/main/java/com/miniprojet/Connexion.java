package com.miniprojet;

import java.sql.*;

public class Connexion {
    static Connection conn= null;
    @SuppressWarnings("exports")
    public static Connection getConnexion() {
        try {
            conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/MiniProject_Java", "root", "");
            System.out.println("Connexion établie");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
