package com.todolist;

import java.io.IOException;

public class LoggedApp {
    private User currentUser;

    public LoggedApp(User user){
        this.currentUser = user;
    }

    public void init() {
        App.printBanner();
        System.out.println(">> Hello, " + this.currentUser.getName() + "!");

        int input = 0;

        while(input!=3){

            Tasks.printTasks(this.currentUser);

            homeOptionsPrinter();

            System.out.print("   > ");
            try {
                input = App.inputScanner.nextInt();
                App.inputScanner.nextLine();
            } catch (RuntimeException e){
                System.out.println("[ERROR] Invalid input\n");
                continue;
            }

//            System.out.println();
            homeOptionsHandler(input);
            System.out.println();
        }

        App.isLogged = false;
    }

    private static void homeOptionsPrinter(){
        System.out.println("    [1] Add new task");
        System.out.println("    [2] Finish task");
        System.out.println("    [3] Exit");
    }

    private void homeOptionsHandler(int option){
        UserRepository userRepository = new UserRepositoryImpl();
        switch (option) {
            case 1:
                System.out.print("Task: ");
                String input = App.inputScanner.nextLine();

                userRepository.createTask(this.currentUser, input);
                this.currentUser.updateUserTaskList();
                break;
            case 2:
                System.out.print("Task ID: #");
                int taskID = App.inputScanner.nextInt();
                App.inputScanner.nextLine();

                int realTaskId = this.currentUser.getRealTaskId(taskID);
                if( realTaskId == -1){
                    System.out.println("[ERROR] Tasks not exists.");
                    return;
                }
                if(userRepository.deleteTask(realTaskId)){
                    this.currentUser.updateUserTaskList();
                    System.out.println("Task deleted.");
                }
                break;
        }
    }
}