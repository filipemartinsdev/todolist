package com.todolist;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Tasks {

//    --- Standard method to print the tasks ---
    public static void printTasks(User user){
        UserRepository userRepository = new UserRepositoryImpl();

        if(!userRepository.userExists(user.getName())){
            return;
        }

        if(user.getUserTaskMap() == null){
            return;
        }

        Set<Map.Entry<Integer, String>> taskSet = user.getUserTaskMap().entrySet();

        int i = 1;
        for(Map.Entry entryTask:taskSet){
            printTaskBlock(entryTask.getValue().toString(), i);
            i++;
        }
    }

//    --- Standard method to create a task ---
//    public static void createTask(String username, int userId, String details){
//        // TODO: use preparedStatement
//        if(details.length() > 100) {
//            System.out.println("[ERROR] Too long text");
//            return;
//        }
//        details = App.catchSQLInjection(details);
//        try {
//            Connection dataBaseConnection = DriverManager.getConnection(App.JDBC_URL, App.DB_USER, App.DB_PASSWORD);
//            Statement dataBaseStatement = dataBaseConnection.createStatement();
//
//            int result = dataBaseStatement.executeUpdate(
//                    "INSERT INTO tasks (user_name, details, user_id) VALUES ('"+username+"', '"+details+"', "+userId+");"
//            );
//
//            dataBaseConnection.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    --- util method / In = current task_id / Out = real task_ID ---
//    private static int getRealTaskID(int currentID, int userID){
//        int out = -1;
//
//        try {
//            dataBaseConnection = DriverManager.getConnection(App.JDBC_URL, App.DB_USER, App.DB_PASSWORD);
//            Statement statement = dataBaseConnection.createStatement();
//            ResultSet result = statement.executeQuery("SELECT id FROM tasks WHERE user_id = "+userID+" ORDER BY create_time;");
//            result.next();
//
//            int i = 1;
//            while(i < currentID){
//                if(result.isLast()) return out;
//                result.next();
//                i++;
//            }
//            out = result.getInt(1);
//
//        } catch (SQLException e) {
////            throw new RuntimeException(e);
//            System.out.println("Task not found.");
//        }
//        return out;
//    }

    private static void printTaskBlock(String text, int taskId){
        int maxLength = 40;
        int currentLength=0;
        int lengthRest = maxLength;

        String[] wordList = text.split(" ");

        System.out.print("+");
        for(int i=0; i<=maxLength/2-1; i++){
            System.out.print("-");
        }
        System.out.print("#"+taskId);
        for(int i=0; i<=maxLength/2-1; i++){
            System.out.print("-");
        }
        System.out.print("+");
        System.out.println();
        System.out.print("| ");

        for(String word:wordList){
            if (word.length()<=lengthRest){
                currentLength += word.length()+1;
                lengthRest = maxLength - currentLength;

                System.out.print(word+" ");
                continue;
            }

            for(int i=0; i<=lengthRest;i++){
                System.out.print(" ");
            }
            System.out.print("|");

            System.out.print("\n| "+word+" ");
            currentLength=word.length()+1;
            lengthRest = maxLength - currentLength;
        }

        for(int i=0; i<=lengthRest;i++){
            System.out.print(" ");
        }
        System.out.print("|\n+");
        for(int i=0; i<maxLength+2; i++){
            System.out.print("-");
        }
        System.out.print("+\n");
    }
}