import java.sql.*;
import java.util.Scanner;

public class App {
    protected static final String JDBC_URL = "jdbc:postgresql://localhost:5432/todolist";
    protected static final String DB_USER = "postgres";
    protected static final String DB_PASSWORD = "";

    protected static Scanner inputScanner = new Scanner(System.in);

    protected static boolean isLogged = false;

    protected static String currentUser;

    public static void init(){
        while(!isLogged){
            homeOptionsPrinter();

            System.out.print("> ");
            int input = inputScanner.nextInt();
            inputScanner.nextLine();

            homeOptionsHandler(input);
        }

        LoggedApp currentLogin = new LoggedApp(currentUser, getUserID(currentUser));
        currentLogin.init();

        finish();
    }

    private static void finish(){
        System.out.println("Session closed.");
        inputScanner.close();
    }

    protected static int getUserID(String username){
        try {
            Connection dataBaseConnection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            Statement statement = dataBaseConnection.createStatement();
            ResultSet result = statement.executeQuery("SELECT id FROM users WHERE name = '"+username+"';");

            if(result.isBeforeFirst()) {
                result.next();
                return result.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
//    private static void inputHandler(String input){}

    private static void homeOptionsPrinter(){
        System.out.println("\n+------------+");
        System.out.println("| TO DO LIST |");
        System.out.println("+------------+");

        System.out.println("What to do? ");
        System.out.println("[1] Login");
        System.out.println("[2] Create account");
        System.out.println("[3] Exit");
    }

    private static void homeOptionsHandler(int option){
        String user;
        String password;
        switch (option){
            case 1: // LOGIN
                System.out.print("User: ");
                user = App.inputScanner.nextLine();
                System.out.print("Password: ");
                password = App.inputScanner.nextLine();

                System.out.println("Loading...");

                if (!Login.userExists(user)){
                    System.out.println("[ERROR] User not exists.");
                }
                else if(Login.userExists(user) && !Login.validatePassword(user, password)){
                    System.out.println("[ERROR] Incorrect password.");
                }
                else if(Login.userExists(user) && Login.validatePassword(user, password)){
                    System.out.println("Connecting...");
                    isLogged = true;
                    currentUser = user;
                }
                return;
            case 2: // REGISTER
                System.out.print("Username: ");
                user = inputScanner.nextLine();

                System.out.print("Password: ");
                password = inputScanner.nextLine();

                System.out.print("Repeat password: ");
                String repeatPassword = inputScanner.nextLine();

                if(!password.equals(repeatPassword)){
                    System.out.println("[ERROR] The passwords are not equals.");
                }
                else {
                    if(Login.registerUser(user, password)){
                        System.out.println("User created!");
                    }
                }
                return;
        }
    }
}
