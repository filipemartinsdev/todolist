public class LoggedApp {
    String username;
    int userID;

    public LoggedApp(String user, int ID){
        this.username = user;
        this.userID = ID;
    }

    public void init() {
        App.printBanner();
        System.out.println(">> Hello, " + this.username + "!");

        int input = 0;

        while(input!=3){

            Tasks.printTasks(this.username);

            homeOptionsPrinter();

            System.out.print("   > ");
            input = App.inputScanner.nextInt();
            App.inputScanner.nextLine();

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
        switch (option) {
            case 1:
                System.out.print("Task: ");
                String input = App.inputScanner.nextLine();

                Tasks.createTask(this.username, this.userID, input);
                break;
            case 2:
                System.out.print("Task ID: #");
                int taskID = App.inputScanner.nextInt();
                App.inputScanner.nextLine();

                Tasks.finishTask(taskID, this.userID);
                break;
        }
    }
}