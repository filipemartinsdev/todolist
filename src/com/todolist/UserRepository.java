package com.todolist;

import java.util.Map;

public interface UserRepository {
    public User getUser(String username);
//    public User getUserById(int userId);

    public int getUserId(String username);

    public boolean userExists(String username);

    public boolean deleteUser(String username);

    public boolean validatePassword(String username, String password);

    public boolean registerUser(String username, String password);

    public Map<Integer, String> getUserTasks(String username);

    public boolean createTask(User user, String text);

    public boolean deleteTask(int taskId);
}
