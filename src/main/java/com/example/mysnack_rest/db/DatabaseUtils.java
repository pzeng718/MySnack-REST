package com.example.mysnack_rest.db;

import java.sql.*;

public class DatabaseUtils {
    public static ResultSet getQueryResults(Connection connection, final String query){
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            return resultSet;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public static boolean performDBUpdate(Connection connection, String query, String... params){
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(query);

            int index = 1;
            for(String param : params){
                statement.setString(index, param);
                index ++;
            }

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(statement);
            e.printStackTrace();
            return false;
        }
    }
}
