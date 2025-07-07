package com.todolist;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/todolist";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "020407"; // FIXME remove the password

    private static Connection dataBaseConnection;
    int userID;


    @Override
    public User getUser(String username) {
        String sql = "SELECT * FROM users WHERE name = ?;";
        try {
            dataBaseConnection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            PreparedStatement statement = dataBaseConnection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            dataBaseConnection.close();
            if(result.next()){
                return new User(result.getInt(1), username);
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public int getUserId(String username) {
        String sql = "SELECT * FROM users WHERE name = ?;";
        try {
            dataBaseConnection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            PreparedStatement statement = dataBaseConnection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            dataBaseConnection.close();
            if(result.next()){
                return result.getInt(1);
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return -1;
    }

    @Override
    public boolean userExists(String username) {
        String sql = "SELECT * FROM users WHERE name = ?";
        try {
            dataBaseConnection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = dataBaseConnection.prepareStatement(sql);
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();
            dataBaseConnection.close();
            if(result.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        String sql = "DELETE FROM users WHERE name = ?;";
        try {
            dataBaseConnection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = dataBaseConnection.prepareStatement(sql);
            statement.setString(1, username);

            boolean isDeleted = statement.execute();
            dataBaseConnection.close();
            if(isDeleted) return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean validatePassword(String username, String password) {
        String sql = "SELECT * FROM users WHERE name = ?;";
        try {
            dataBaseConnection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = dataBaseConnection.prepareStatement(sql);
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();
            dataBaseConnection.close();

            if(result.next()){
                if(result.getString(3).equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean registerUser(String username, String password){
        if(userExists(username)){
            System.out.println("[ERROR] This user already exists.\n");
            return false;
        }

        String sql = "INSERT INTO users (name, password) VALUES (?, ?);";
        try {
            dataBaseConnection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = dataBaseConnection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(12, password);
            int result = statement.executeUpdate();
            if(result>0) return true;
            dataBaseConnection.close();
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println("[ERROR] Connection Error.");
            System.out.println("        Try again later.");
        }

        System.out.println("Account NOT created.");
        return false;
    }

    @Override
    public Map<Integer, String> getUserTasks(String username) {
        String sql = "SELECT id, details, create_time FROM tasks WHERE user_name = ? ORDER BY create_time DESC;";
        try {
            dataBaseConnection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = dataBaseConnection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if (result.next()){
                Map<Integer, String> outMap = new HashMap<Integer, String>();

                while(!result.isAfterLast()){
                    outMap.put(result.getInt(1), result.getString(2));
                    result.next();
                }
                return outMap;
            }
            dataBaseConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean createTask(User user, String text) {
        if(!userExists(user.getName())){
            return false;
        }

        String sql = "INSERT INTO tasks (user_id, user_name, details) VALUES (?, ?, ?);";
        try {
            dataBaseConnection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = dataBaseConnection.prepareStatement(sql);
            statement.setInt(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, text);

            int result = statement.executeUpdate();
            if(result>0){
                dataBaseConnection.close();

                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?;";
        try {
            dataBaseConnection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = dataBaseConnection.prepareStatement(sql);
            statement.setInt(1, taskId);
            int result = statement.executeUpdate();

            dataBaseConnection.close();
            if(result>0) return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}