package com.todolist;

import java.util.List;
import java.util.Map;

public class User extends UserRepositoryImpl{
    private final int ID;
    private final String NAME;
//    private final String PASSWORD;

    private Map<Integer, String> taskMap;

    public User(int id, String username){
        this.ID = id;
        this.NAME = username;
//        this.PASSWORD = password;
        this.taskMap = new UserRepositoryImpl().getUserTasks(username);
    }

    public String getName() {
        return this.NAME;
    }

    public int getId(){
        return this.ID;
    }

    public Map<Integer, String> getUserTaskMap(){
        return this.taskMap;
    }

    public Map<Integer, String> updateUserTaskList(){
        this.taskMap = new UserRepositoryImpl().getUserTasks(this.NAME);
        return this.taskMap;
    }

    public int getRealTaskId(int id){
        int i = 0;
        for (int taskId:this.getUserTaskMap().keySet()){
            if (i==id){
                return taskId;
            }
            i++;
        }
        return -1;
    }
}
