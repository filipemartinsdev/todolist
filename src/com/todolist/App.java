package com.todolist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    protected static Scanner inputScanner = new Scanner(System.in);

    protected static boolean isLogged = false;

//    protected static String currentUser;

    protected static User currentUser;


    public static void init(){
        while(!isLogged){
            printBanner();
            homeOptionsPrinter();

            System.out.print("   > ");
            int input = inputScanner.nextInt();
            inputScanner.nextLine();

            homeOptionsHandler(input);
            System.out.println();
        }

        LoggedApp currentLogin = new LoggedApp(currentUser);
        currentLogin.init();

        finish();
    }

    private static void finish(){
        System.out.println("Session closed.");
        inputScanner.close();
    }

    private static void homeOptionsPrinter(){
        System.out.println("    [1] Login");
        System.out.println("    [2] Create account");
        System.out.println("    [3] Exit");
    }

    private static void homeOptionsHandler(int option){
        String username;
        String password;
        if(option == 1) {
            System.out.print("User: ");
            username = App.inputScanner.nextLine();
            System.out.print("Password: ");
            password = App.inputScanner.nextLine();

            System.out.println("Loading...");

            UserRepository userRepository = new UserRepositoryImpl();

            boolean userExists = userRepository.userExists(username);
            boolean passwordIsCorrect = userRepository.validatePassword(username, password);

            if (!userExists) {
                System.out.println("[ERROR] User not exists.");
            } else if (userExists && !passwordIsCorrect) {
                System.out.println("[ERROR] Incorrect password.");
            } else if (userExists && passwordIsCorrect) {
                System.out.println("Connecting...");

                isLogged = true;

                currentUser = new User(userRepository.getUserId(username), username);
            }
        }

            // REGISTER
        else if(option == 2) {
            System.out.print("Username: ");
            username = inputScanner.nextLine();

            System.out.print("Password: ");
            password = inputScanner.nextLine();

            System.out.print("Repeat password: ");
            String repeatPassword = inputScanner.nextLine();

            if (!password.equals(repeatPassword)) {
                System.out.println("[ERROR] The passwords are not equals.");
            } else {
                UserRepository userRepository = new UserRepositoryImpl();

                if (userRepository.userExists(username)) {
                    System.out.println("[ERROR] User already exists.");
                } else if (userRepository.registerUser(username, password)) {
                    System.out.println("User created!");
                }
            }
        }
    }

    protected static void printBanner(){
        System.out.println(
                " _____           _         _     _     _   \n" +
                        "|_   _|         | |       | |   (_)   | |  \n" +
                        "  | | ___     __| | ___   | |    _ ___| |_ \n" +
                        "  | |/ _ \\   / _` |/ _ \\  | |   | / __| __|\n" +
                        "  | | (_) | | (_| | (_) | | |___| \\__ \\ |_ \n" +
                        "  \\_/\\___/   \\__,_|\\___/  \\_____/_|___/\\__|\n"
        );
    }

//  --- Return a String without possibles SQLInjection ---
    protected static String catchSQLInjection(String text){
        StringBuilder strOut = new StringBuilder();
        char[] inputAsCharArr = text.toCharArray();
        List<Character> outAsList = new ArrayList<Character>();

        for(char c:inputAsCharArr){
            if(c == '\'') {
                outAsList.add('\'');
                outAsList.add('\'');
                continue;
            }
            outAsList.add(c);
        }

        for(char c:outAsList){
            strOut.append(c);
        }

        return strOut.toString();
    }
}