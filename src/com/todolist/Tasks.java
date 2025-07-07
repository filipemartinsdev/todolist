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