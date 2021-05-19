package com.example.mysnack_rest.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/mysnack?allowPublicKeyRetrieval=true&useSSL=false",
                    "mytestuser", "My6$Password");
            // TODO: Need to change to the username and password for your database connection
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
