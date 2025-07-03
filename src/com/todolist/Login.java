package com.todolist;

import java.sql.*;

public final class Login {
    public static boolean userExists(String username){
        boolean exists = true;
        try {
            Connection dataBaseConnection = DriverManager.getConnection(App.JDBC_URL, App.DB_USER, App.DB_PASSWORD);

            Statement statement = dataBaseConnection.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM users WHERE name = '"+username+"';");
            result.next();

            exists = result.isLast();
            dataBaseConnection.close();
        } catch (SQLException e) {
            System.out.println("[DB ERROR] "+e.getSQLState());
            throw new RuntimeException(e);
        }
        return exists;
    }

    public static boolean validatePassword(String username, String password){
        try {
            Connection dataBaseConnection = DriverManager.getConnection(App.JDBC_URL, App.DB_USER, App.DB_PASSWORD);

            Statement statement = dataBaseConnection.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM users WHERE name = '"+username+"';");
            result.next();
            if(result.getString(3).equals(password)){
                return true;
            }
        } catch (SQLException e) {
            return false;
//            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean registerUser(String username, String password){
        if(userExists(username)){
            System.out.println("[ERROR] This user already exists.\n");
            return false;
        }

        try {
            Connection dataBaseConnection = DriverManager.getConnection(App.JDBC_URL, App.DB_USER, App.DB_PASSWORD);
            Statement statement = dataBaseConnection.createStatement();

            int result = statement.executeUpdate("INSERT INTO users (name, password) VALUES ('"+username+"', '"+password+"');");
            if(result>0) return true;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println("[ERROR] Connection Error.");
            System.out.println("        Try again later.");
        }

        System.out.println("Account NOT created.");
        return false;
    }
}
